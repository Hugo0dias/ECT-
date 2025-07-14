from typing import NamedTuple, Optional
from pyodbc import IntegrityError
from bd_project.session import create_connection

class Coin(NamedTuple):
    id: int
    value: float
    country_name: str
    mintage: int
    condition: str
    material: str
    finishing: str
    market_price: float
    id_colection: int

class AuthorInfo(NamedTuple):
    nif: int
    name: str
    nationality: str

class CommonFace(NamedTuple):
    description: str
    version: Optional[str]
    authors: list[AuthorInfo]

class NationalFace(NamedTuple):
    year: int
    description: str
    comemorative: bool
    mint_mark: Optional[str]
    authors: list[AuthorInfo]

class CollectionInfo(NamedTuple):
    creation_date: str
    designation: str

class CoinDetails(NamedTuple):
    id: int
    value: float
    country_name: str
    mintage: int
    condition: str
    material: str
    finishing: str
    market_price: float
    id_colection: int
    common_face: Optional[CommonFace]
    nacional_face: Optional[NationalFace]
    collection_info: Optional[CollectionInfo]

##############################
def get_authors_for_face(cursor, coin_id, description, face_type):
    table = "CF_Has_Author" if face_type == "common" else "NF_Has_Author"
    nif_column = "NIF_Author_CF" if face_type == "common" else "NIF_Author_NF"

    cursor.execute(f"""
        SELECT A.NIF, A.Fname, A.Nacionality
        FROM {table} FA
        JOIN Author A ON A.NIF = FA.{nif_column}
        WHERE FA.Coin_ID = ? AND FA.[Description] = ?
    """, (coin_id, description))

    return [
        AuthorInfo(nif=row[0], name=row[1], nationality=row[2])
        for row in cursor.fetchall()
    ]

def get_common_face(cursor, coin_id) -> Optional[CommonFace]:
    cursor.execute("""
        SELECT [Description], [Version]
        FROM Common_Face
        WHERE Coin_ID = ?
    """, (coin_id,))
    row = cursor.fetchone()
    if not row:
        return None
    authors = get_authors_for_face(cursor, coin_id, row[0], "common")
    return CommonFace(description=row[0], version=row[1], authors=authors)

def get_nacional_face(cursor, coin_id) -> Optional[NationalFace]:
    cursor.execute("""
        SELECT [Year], [Description], Comemorative, Mint_Mark
        FROM Nacional_Face
        WHERE Coin_ID = ?
    """, (coin_id,))
    row = cursor.fetchone()
    if not row:
        return None
    authors = get_authors_for_face(cursor, coin_id, row[1], "national")
    return NationalFace(year=row[0], description=row[1], comemorative=bool(row[2]), mint_mark=row[3], authors=authors)

def get_collection_info(cursor, collection_id) -> Optional[CollectionInfo]:
    cursor.execute("""
        SELECT Creation_Date, T.Designation
        FROM Colection C
        JOIN [Type] T ON C.Code_Type = T.Code_Type
        WHERE C.ID = ?
    """, (collection_id,))
    row = cursor.fetchone()
    if not row:
        return None
    return CollectionInfo(creation_date=row[0], designation=row[1])
###################################



def list_coins() -> list[Coin]:
    """List all coins in the database"""
    with create_connection() as conn:
        with conn.cursor() as cursor:
            cursor.execute("""
                SELECT ID, [Value], Country_Name, Mintage, [Condition], 
                       Material, Finishing, Market_Price, ID_Colection
                FROM Coin
                ORDER BY Market_Price DESC
            """)
            return [Coin(*row) for row in cursor.fetchall()]

def search_coins_in_db(filters: dict) -> list[Coin]:
    """Search coins with multiple filters"""
    with create_connection() as conn:
        with conn.cursor() as cursor:
            query = """
                SELECT ID, [Value], Country_Name, Mintage, [Condition], 
                       Material, Finishing, Market_Price, ID_Colection
                FROM Coin
                WHERE 1=1
            """
            params = []
            
            # Adicionar filtros dinamicamente
            if 'coin_id' in filters:
                query += " AND ID = ?"
                params.append(int(filters['coin_id']))
                
            if 'value' in filters:
                query += " AND [Value] = ?"
                params.append(float(filters['value']))
                
            if 'country_name' in filters:
                query += " AND Country_Name LIKE ?"
                params.append(f"%{filters['country_name']}%")
                
            if 'mintage_min' in filters:
                query += " AND Mintage >= ?"
                params.append(int(filters['mintage_min']))
                
            if 'mintage_max' in filters:
                query += " AND Mintage <= ?"
                params.append(int(filters['mintage_max']))
                
            if 'condition' in filters:
                query += " AND [Condition] = ?"
                params.append(filters['condition'])
                
            if 'material' in filters:
                query += " AND Material = ?"
                params.append(filters['material'])
                
            if 'finishing' in filters:
                query += " AND Finishing = ?"
                params.append(filters['finishing'])
                
            if 'price_min' in filters:
                query += " AND Market_Price >= ?"
                params.append(float(filters['price_min']))
                
            if 'price_max' in filters:
                query += " AND Market_Price <= ?"
                params.append(float(filters['price_max']))
                
            if 'collection_id' in filters:
                query += " AND ID_Colection = ?"
                params.append(int(filters['collection_id']))
                
            query += " ORDER BY Market_Price DESC"
            
            cursor.execute(query, params)
            return [Coin(*row) for row in cursor.fetchall()]

def create_coin(coin: Coin):
    """Create a new coin record"""
    with create_connection() as conn:
        with conn.cursor() as cursor:
            try:
                # First ensure the country exists
                cursor.execute(
                    "IF NOT EXISTS (SELECT 1 FROM Country WHERE [Name] = ?) "
                    "INSERT INTO Country ([Name]) VALUES (?)", 
                    (coin.country_name, coin.country_name)
                )
                
                # Then insert the coin
                cursor.execute("""
                    INSERT INTO Coin (
                        ID, [Value], Country_Name, Mintage, [Condition], 
                        Material, Finishing, Market_Price, ID_Colection
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, (
                    coin.id, coin.value, coin.country_name, coin.mintage,
                    coin.condition, coin.material, coin.finishing, 
                    coin.market_price, coin.id_colection
                ))
                conn.commit()
            except IntegrityError as e:
                conn.rollback()
                raise ValueError(f"Failed to create coin: {str(e)}")

def detail_coin(coin_id: int) -> Optional[CoinDetails]:
    """Get detailed information about a specific coin"""
    with create_connection() as conn:
        with conn.cursor() as cursor:
            cursor.execute("""
                SELECT ID, [Value], Country_Name, Mintage, [Condition],
                       Material, Finishing, Market_Price, ID_Colection
                FROM Coin
                WHERE ID = ?
            """, (coin_id,))
            row = cursor.fetchone()
            if not row:
                return None

            common_face = get_common_face(cursor, row[0])
            nacional_face = get_nacional_face(cursor, row[0])
            collection_info = get_collection_info(cursor, row[8])

            return CoinDetails(
                id=row[0],
                value=row[1],
                country_name=row[2],
                mintage=row[3],
                condition=row[4],
                material=row[5],
                finishing=row[6],
                market_price=row[7],
                id_colection=row[8],
                common_face=common_face,
                nacional_face=nacional_face,
                collection_info=collection_info
            )

def get_coin_by_id(coin_id: int) -> Coin:
    """Get a single coin by its ID"""
    with create_connection() as conn:
        with conn.cursor() as cursor:
            cursor.execute("""
                SELECT ID, [Value], Country_Name, Mintage, [Condition], 
                       Material, Finishing, Market_Price, ID_Colection
                FROM Coin
                WHERE ID = ?
            """, (coin_id,))
            row = cursor.fetchone()
            return Coin(*row) if row else None

def update_coin(coin: Coin):
    """Update an existing coin record"""
    with create_connection() as conn:
        with conn.cursor() as cursor:
            try:
                cursor.execute("""
                    UPDATE Coin SET
                        [Value] = ?,
                        Country_Name = ?,
                        Mintage = ?,
                        [Condition] = ?,
                        Material = ?,
                        Finishing = ?,
                        Market_Price = ?,
                        ID_Colection = ?
                    WHERE ID = ?
                """, (
                    coin.value, coin.country_name, coin.mintage,
                    coin.condition, coin.material, coin.finishing,
                    coin.market_price, coin.id_colection, coin.id
                ))
                conn.commit()
            except IntegrityError as e:
                conn.rollback()
                raise ValueError(f"Failed to update coin: {str(e)}")

def delete_coin(coin_id: int):
    """Delete a coin record"""
    with create_connection() as conn:
        with conn.cursor() as cursor:
            try:
                # First delete associated faces if they exist
                cursor.execute("DELETE FROM Common_Face WHERE Coin_ID = ?", (coin_id,))
                cursor.execute("DELETE FROM Nacional_Face WHERE Coin_ID = ?", (coin_id,))
                
                # Then delete the coin
                cursor.execute("DELETE FROM Coin WHERE ID = ?", (coin_id,))
                conn.commit()
            except IntegrityError as e:
                conn.rollback()
                raise ValueError(f"Failed to delete coin: {str(e)}")

def list_conditions() -> list[str]:
    """List all valid coin conditions"""
    return ['VB', 'B', 'G', 'VG', 'F', 'VF', 'AU', 'UNC']

def list_finishings() -> list[str]:
    """List all valid coin finishings"""
    return ['Proof', 'Normal', 'FDC', 'BU', 'BNC']

def list_materials() -> list[str]:
    """List all valid coin materials"""
    return [
        'Aluminum Bronze', 'Steel', 'CuproNiquel', 'Silver', 'Gold', 
        'Platinum', 'Bullion', 'Palladium', 'Nickel-brass', 
        'Bicolor Clad', 'Bimetallic', 'Nickel'
    ]