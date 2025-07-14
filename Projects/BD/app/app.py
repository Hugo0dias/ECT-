import datetime
import traceback
from functools import wraps

import pyodbc
from bd_project.coin import *
from bd_project.session import create_connection
from flask import (Flask, flash, redirect, render_template, request, session,
                   url_for, jsonify)

app = Flask(__name__)
app.secret_key = 'chave' 


# Configuração do tema Bootstrap
BOOTSTRAP_THEME = "https://cdn.jsdelivr.net/npm/bootswatch@5.2.3/dist/sandstone/bootstrap.min.css"

# Página inicial
app = Flask(__name__)
app.secret_key = 'chave' 


# Configuração do tema Bootstrap
BOOTSTRAP_THEME = "https://cdn.jsdelivr.net/npm/bootswatch@5.2.3/dist/sandstone/bootstrap.min.css"

# Página inicial
@app.route("/", methods=['GET', 'POST'])
def home():
    if request.method == 'POST':
        nif = request.form.get('nif')
        password_input = request.form.get('password')

        conn = create_connection()
        cursor = conn.cursor()
        
        # Chama a SP para obter a password desencriptada
        cursor.execute("EXEC dbo.sp_DecryptPasswordByNIF ?", (nif,))
        row = cursor.fetchone()
        conn.close()

        if row:
            decrypted_password = row.DecryptedPassword
            if decrypted_password == password_input:
                session['user_nif'] = nif

                flash('Login realizado com sucesso!', 'success')
                return redirect(url_for('colecoes'))
            else:
                flash('Password incorreta. Por favor, tente novamente.', 'danger')
        else:
            flash('NIF não encontrado. Por favor, tente novamente.', 'danger')

    return render_template('login.html', theme=BOOTSTRAP_THEME)



@app.route("/register", methods=["GET", "POST"])
def register():
    if request.method == "POST":
        nif = request.form.get("nif")
        fname = request.form.get("fname")
        lname = request.form.get("lname")
        email = request.form.get("email")
        bdate = request.form.get("bdate")
        phone = request.form.get("phone")
        password = request.form.get("password")

        try:
            conn = create_connection()
            cursor = conn.cursor()

            # Chamar a stored procedure com transação
            cursor.execute(""" EXEC [Numismatics].[sp_RegisterUser] ?, ?, ?, ?, ?, ?, ?; """, (nif, fname, lname, email, bdate, phone, password))
            conn.commit()

            flash("Registo efetuado com sucesso! Faça login.", "success")
            return redirect(url_for("home"))

        except Exception as e:
            conn.rollback()
            flash(f"Ocorreu um erro: {str(e)}", "danger")
            return redirect(url_for("register"))

        finally:
            conn.close()

    return render_template("register.html", theme=BOOTSTRAP_THEME)



@app.route('/colecoes')
def colecoes():
    print("⚠️ ENTROU NA ROTA /colecoes")
    if 'user_nif' not in session:
        flash('Você precisa estar logado para acessar suas coleções.', 'danger')
        return redirect(url_for('home'))

    nif = session['user_nif']
    colecoes = get_colecoes_by_nif(nif)
    print("Usuário logado NIF:", session.get('user_nif'))
    print("Coleções encontradas:", colecoes)
    return render_template('user_coins.html', colecoes=colecoes)



def get_colecoes_by_nif(nif):
    try:
        conn = create_connection()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM [Numismatics].fn_GetCollectionsByNIF(?);", (nif,))
        rows = cursor.fetchall()

        # Transformar os resultados em uma lista de dicionários
        colecoes = [
            {
                'id': row.id,
                'creation_date': row.creation_date,
                'code_type': row.code_type,
                'type': row.type,
                'total_coins': row.total_coins,
                'total_CC_coins': row.total_CC_coins
            }
            for row in rows
        ]
        return colecoes
    except Exception as e:
        return []


@app.route("/logout")
def logout():
    session.pop('user_nif', None)
    flash('Você foi deslogado com sucesso', 'success')
    return redirect(url_for('home'))

def login_required(f):
    @wraps(f)
    def decorated_function(*args, **kwargs):
        if 'user_nif' not in session:
            flash('Por favor, faça login para acessar esta página', 'danger')
            return redirect(url_for('home'))
        return f(*args, **kwargs)
    return decorated_function

@app.route('/user_profile', methods=['GET'])
def user_profile():
    if 'user_nif' not in session:
        flash('Por favor faça login para acessar esta página', 'danger')
        return redirect(url_for('home'))
    
    nif_user = session['user_nif']
    conn = create_connection()

    try:
        cursor = conn.cursor()
        
        # Obter nome completo usando a função
        cursor.execute("SELECT dbo.fn_GetUserFullName(?) AS FullName", (nif_user,))
        full_name = cursor.fetchone().FullName

        # Info do usuário
        cursor.execute("SELECT Fname, Lname, Email, BDate, Phone FROM [Numismatics].[User] WHERE NIF = ?", (nif_user,))
        user = cursor.fetchone()

        if not user:
            flash('Usuário não encontrado', 'danger')
            return redirect(url_for('home'))

        # Total moedas
        cursor.execute("""
            SELECT COUNT(*) 
            FROM [Numismatics].Coin c
            JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
            WHERE col.NIF_User = ?
        """, (nif_user,))
        total_coins = cursor.fetchone()[0]

        # Comemorativas
        cursor.execute("""
            SELECT COUNT(*) 
            FROM [Numismatics].Coin c
            JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
            JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
            WHERE col.NIF_User = ? AND nf.Comemorative = 1
        """, (nif_user,))
        total_comemorativas = cursor.fetchone()[0]

        # Coleções com autores
        cursor.execute("""
            SELECT 
                c.ID,
                t.Designation,
                c.Creation_Date,
                dbo.fn_GetCollectionValue(c.ID) AS TotalValue
            FROM [Numismatics].Colection c
            JOIN [Numismatics].[Type] t ON c.Code_Type = t.Code_Type
            WHERE c.NIF_User = ?
            ORDER BY c.Creation_Date DESC
        """, (nif_user,))
        colecoes = []
        for row in cursor.fetchall():
            # Para cada coleção, buscar os autores
            cursor.execute("SELECT * FROM dbo.fn_GetAuthorsOfCollection(?)", (row.ID,))
            autores = [dict(zip([column[0] for column in cursor.description], row)) 
                      for row in cursor.fetchall()]
            
            colecoes.append({
                'id': row.ID,
                'designation': row.Designation,
                'creation_date': row.Creation_Date,
                'total_value': row.TotalValue,
                'autores': autores
            })

        # Moedas por país/currency
        cursor.execute("""
            SELECT DISTINCT curr.Currency_Name, curr.Country_Name, curr.ISO_Code,
                            curr.Date_First_Edition, curr.Date_Last_Edition
            FROM [Numismatics].[User] u
            JOIN [Numismatics].Colection col ON u.NIF = col.NIF_User
            JOIN [Numismatics].Coin c ON col.ID = c.ID_Colection
            JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID AND c.ID_Colection = nf.ID_Colection
            LEFT JOIN [Numismatics].Currency curr ON c.Country_Name = curr.Country_Name
                                    AND nf.[Year] >= curr.Date_First_Edition
                                    AND (curr.Date_Last_Edition IS NULL OR nf.[Year] <= curr.Date_Last_Edition)
            WHERE u.NIF = ?
            ORDER BY curr.Country_Name
        """, (nif_user,))
        currencies = cursor.fetchall()

        # Coleções por valor
        cursor.execute("""
            SELECT c.ID AS Colecao_ID, t.Designation, c.Creation_Date, 
                   SUM(co.Market_Price) AS Valor_Total
            FROM [Numismatics].Colection c
            JOIN [Numismatics].[Type] t ON c.Code_Type = t.Code_Type
            JOIN [Numismatics].Coin co ON co.ID_Colection = c.ID
            WHERE c.NIF_User = ?
            GROUP BY c.ID, t.Designation, c.Creation_Date
            ORDER BY Valor_Total DESC
        """, (nif_user,))
        colecao_valor = cursor.fetchall()

        # Materiais usados
        cursor.execute("""
            SELECT DISTINCT Material
            FROM [Numismatics].Coin c
            JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
            WHERE col.NIF_User = ?
            ORDER BY Material
        """, (nif_user,))
        materiais = cursor.fetchall()

        cursor.execute("SELECT ID FROM [Numismatics].Colection WHERE NIF_User = ?", (nif_user,))
        row = cursor.fetchone()

        if row:
            collection_id = row.ID
            cursor.execute("SELECT * FROM dbo.fn_GetEventParticipation(?, 0)", (collection_id,))
            eventos = [dict(zip([column[0] for column in cursor.description], row)) 
                       for row in cursor.fetchall()]
        else:
            eventos = []


    except Exception as e:
        flash(f'Erro ao carregar perfil: {str(e)}', 'danger')
        return redirect(url_for('home'))
    finally:
        conn.close()

    return render_template('profile.html',
                           full_name=full_name,
                           user=user,
                           total_coins=total_coins,
                           total_comemorativas=total_comemorativas,
                           colecoes=colecoes,
                           currencies=currencies,
                           colecao_valor=colecao_valor,
                           materiais=materiais,
                           eventos=eventos,
                           theme=BOOTSTRAP_THEME)


@app.route("/coins", methods=['GET', 'POST'], endpoint='list_all_coins')
def list_all_coins():
    # Verifica se o usuário está logado
    if 'user_nif' not in session:
        flash('Por favor, faça login para acessar esta página', 'danger')
        return redirect(url_for('home'))
    
    # Obtém o NIF do usuário da sessão
    nif_user = session['user_nif']
    
    if request.method == 'POST':
        # Obter parâmetros do formulário de filtro
        id_colecao = request.form.get('id_colecao')
        pais = request.form.get('pais')
        material = request.form.get('material')
        condicao = request.form.get('condicao')
        mintage_min = request.form.get('mintage_min')
        mintage_max = request.form.get('mintage_max')
        preco_min = request.form.get('preco_min')
        preco_max = request.form.get('preco_max')
        acabamento = request.form.get('acabamento')
        comemorativa = request.form.get('comemorativa')

        conn = create_connection()
        cursor = conn.cursor()
        
        # Query base com JOIN para incluir informações da coleção
        query = """
            SELECT 
                Coin.ID,
                Coin.[Value],
                Coin.Country_Name,
                Coin.Material,
                Coin.[Condition],
                Coin.Mintage,
                Coin.Market_Price,
                Coin.Finishing,
                Colection.ID as ColectionID,
                CASE WHEN EXISTS (
                    SELECT 1 FROM [Numismatics].Nacional_Face 
                    WHERE [Numismatics].Nacional_Face.Coin_ID = Coin.ID 
                      AND [Numismatics].Nacional_Face.ID_Colection = Coin.ID_Colection 
                      AND [Numismatics].Nacional_Face.Comemorative = 1
                ) THEN 1 ELSE 0 END AS Comemorative
            FROM [Numismatics].Coin
            JOIN [Numismatics].Colection ON Coin.ID_Colection = Colection.ID
            WHERE Colection.NIF_User = ?
        """
        params = [nif_user]
        
        # Adicionar filtros dinamicamente
        if id_colecao:
            query += " AND Colection.ID = ?"
            params.append(id_colecao)
        
        if pais:
            query += " AND Coin.Country_Name LIKE ?"
            params.append(f"%{pais}%")
        
        if material and material != "Todos":
            query += " AND Coin.Material = ?"
            params.append(material)
        
        if condicao and condicao != "Todas":
            query += " AND Coin.[Condition] = ?"
            params.append(condicao)
        
        if mintage_min:
            query += " AND Coin.Mintage >= ?"
            params.append(mintage_min)
        
        if mintage_max:
            query += " AND Coin.Mintage <= ?"
            params.append(mintage_max)
        
        if preco_min:
            query += " AND Coin.Market_Price >= ?"
            params.append(preco_min)
        
        if preco_max:
            query += " AND Coin.Market_Price <= ?"
            params.append(preco_max)
        
        if acabamento and acabamento != "Todos":
            query += " AND Coin.Finishing = ?"
            params.append(acabamento)

        if comemorativa and comemorativa != "Todas":
            if comemorativa == "Sim":
                query += """ AND EXISTS (
                    SELECT 1 FROM [Numismatics].Nacional_Face 
                    WHERE [Numismatics].Nacional_Face.Coin_ID = Coin.ID 
                      AND [Numismatics].Nacional_Face.ID_Colection = Coin.ID_Colection 
                      AND [Numismatics].Nacional_Face.Comemorative = 1
                )"""
            else:
                query += """ AND NOT EXISTS (
                    SELECT 1 FROM [Numismatics].Nacional_Face 
                    WHERE [Numismatics].Nacional_Face.Coin_ID = Coin.ID 
                      AND [Numismatics].Nacional_Face.ID_Colection = Coin.ID_Colection 
                      AND [Numismatics].Nacional_Face.Comemorative = 1
                )"""
        
        query += " ORDER BY Coin.ID ASC"
        
        cursor.execute(query, params)
        columns = [column[0] for column in cursor.description]
        moedas = [dict(zip(columns, row)) for row in cursor.fetchall()]
        conn.close()
        
        return render_template('coins_list.html', 
                               coins=moedas,
                               theme=BOOTSTRAP_THEME,
                               show_results=True,
                               form_data=request.form)
    
    # Se for GET, mostrar todas as moedas do usuário logado
    conn = create_connection()
    cursor = conn.cursor()
    cursor.execute("""
        SELECT 
            Coin.ID,
            Coin.[Value],
            Coin.Country_Name,
            Coin.Material,
            Coin.[Condition],
            Coin.Mintage,
            Coin.Market_Price,
            Coin.Finishing,
            Colection.ID as ColectionID,
            CASE WHEN EXISTS (
                SELECT 1 FROM [Numismatics].Nacional_Face 
                WHERE [Numismatics].Nacional_Face.Coin_ID = Coin.ID 
                  AND [Numismatics].Nacional_Face.ID_Colection = Coin.ID_Colection 
                  AND [Numismatics].Nacional_Face.Comemorative = 1
            ) THEN 1 ELSE 0 END AS Comemorative
        FROM [Numismatics].Coin
        JOIN [Numismatics].Colection ON Coin.ID_Colection = Colection.ID
        WHERE Colection.NIF_User = ?
        ORDER BY Coin.ID ASC
    """, (nif_user,))
    columns = [column[0] for column in cursor.description]
    moedas = [dict(zip(columns, row)) for row in cursor.fetchall()]
    conn.close()
    
    return render_template('coins_list.html', 
                           coins=moedas,
                           theme=BOOTSTRAP_THEME,
                           show_results=False,
                           nif_user=nif_user)


@app.route("/search_coins", methods=['GET', 'POST'])
def search_coins():
    
    if request.method == 'POST':
        try:
            # Obter todos os parâmetros do formulário
            filters = {
                'coin_id': request.form.get('coin_id'),
                'value': request.form.get('value'),
                'country_name': request.form.get('country_name'),
                'mintage_min': request.form.get('mintage_min'),
                'mintage_max': request.form.get('mintage_max'),
                'condition': request.form.get('condition'),
                'material': request.form.get('material'),
                'finishing': request.form.get('finishing'),
                'price_min': request.form.get('price_min'),
                'price_max': request.form.get('price_max'),
                'collection_id': request.form.get('collection_id')
            }
            
            # Converter para tipos apropriados
            if filters['coin_id']:
                filters['coin_id'] = int(filters['coin_id'])
            if filters['value']:
                filters['value'] = float(filters['value'])
            if filters['mintage_min']:
                filters['mintage_min'] = int(filters['mintage_min'])
            if filters['mintage_max']:
                filters['mintage_max'] = int(filters['mintage_max'])
            if filters['price_min']:
                filters['price_min'] = float(filters['price_min'])
            if filters['price_max']:
                filters['price_max'] = float(filters['price_max'])
            if filters['collection_id']:
                filters['collection_id'] = int(filters['collection_id'])
            
            # Remover filtros vazios
            filters = {k: v for k, v in filters.items() if v not in [None, '', 0]}
            
            # Aplicar a busca com filtros
            coins = search_coins_in_db(filters)
            
        except ValueError as e:
            flash(f"Erro nos valores dos filtros: {str(e)}", "danger")
            coins = []
        
        return render_template('coin_search.html',
                            coins=coins,
                            filters=filters,
                            conditions=list_conditions(),
                            finishings=list_finishings(),
                            materials=list_materials(),
                            theme=BOOTSTRAP_THEME)
    
    # Se for GET, mostrar formulário vazio
    return render_template('coin_search.html',
                         coins=[],
                         filters={},
                         conditions=list_conditions(),
                         finishings=list_finishings(),
                         materials=list_materials(),
                         theme=BOOTSTRAP_THEME)


@app.route("/colecao/<int:colecao_id>")
def ver_colecao(colecao_id):
    if 'user_nif' not in session:
        flash('Por favor, faça login para visualizar coleções', 'danger')
        return redirect(url_for('login'))

    print("➡️ Acessando rota /colecao/", colecao_id)
    print("🔐 NIF da sessão:", session['user_nif'])

    conn = create_connection()
    cursor = conn.cursor()

    try:
        # 🔍 Buscar dados da coleção
        cursor.execute("""
            SELECT C.ID, C.Creation_Date, T.Designation, U.Fname, U.Lname
            FROM [Numismatics].Colection C
            JOIN [Numismatics].[Type] T ON C.Code_Type = T.Code_Type
            JOIN [Numismatics].[User] U ON C.NIF_User = U.NIF
            WHERE C.ID = ? AND C.NIF_User = ?
        """, (colecao_id, session['user_nif']))
        colecao = cursor.fetchone()

        print("🧾 Coleção encontrada:", colecao)

        if not colecao:
            flash("Coleção não encontrada ou você não tem permissão para visualizá-la", "warning")
            return redirect(url_for('user_profile'))

        # 🪙 Buscar moedas da coleção
        cursor.execute("""
            SELECT c.ID, c.[Value], c.Country_Name, nf.[Year], c.Condition
            FROM [Numismatics].Coin c
            JOIN [Numismatics].Nacional_Face nf ON c.ID = nf.Coin_ID
            WHERE c.ID_Colection = ?
            ORDER BY nf.[Year] DESC, c.[Value] DESC
        """, (colecao_id,))
        colecao_coins = cursor.fetchall()
        print("💰 Total de moedas:", len(colecao_coins))

        # 🌍 Buscar estatísticas de países
        cursor.execute("EXEC GetMissingCountriesByColection @ColectionID = ?", (colecao_id,))
        stats_row = cursor.fetchone()
        countries_stats = {
            'Total_Countries': stats_row[0],
            'TotalCountriesInColection': stats_row[1],
            'Countries_Not_In_Colection': stats_row[0] - stats_row[1]
        }

        print("🌎 Estatísticas de países:", countries_stats)
        cursor.nextset()
        missing_countries = cursor.fetchall()

        print("📊 Estatísticas de países:", countries_stats)
        print("🚩 Países em falta:", [c[0] for c in missing_countries])

        print(f"🧾 Resultado da query de coleção para ID {colecao_id} e NIF {session['user_nif']}: {colecao}")


        return render_template(
            "colecao_detalhes.html", 
            colecao=colecao,
            colecao_coins=colecao_coins,
            countries_stats=countries_stats,
            missing_countries=missing_countries
        )


    except Exception as e:
        print("❌ ERRO NA ROTA /colecao/<id>:")
        print(f"❗ Tipo: {type(e).__name__}")
        print(f"📃 Mensagem: {e}")
        traceback.print_exc()
        flash(f"Erro ao carregar a coleção: {e}", "danger")
        return redirect(url_for('user_profile'))


    finally:
        if conn:
            conn.close()

#TODO Falta passar para SPs e verificar
@app.route('/moeda/<int:coin_id>')
def coin_details(coin_id):
    if 'user_nif' not in session:
        flash('Por favor, faça login para visualizar detalhes', 'danger')
        return redirect(url_for('login'))
    
    conn = create_connection()
    cursor = conn.cursor()
    
    try:
        # Obter informações básicas da moeda
        cursor.execute("""
            SELECT c.*, col.ID AS ColectionID, col.NIF_User
            FROM [Numismatics].Coin c
            JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
            WHERE c.ID = ? AND col.NIF_User = ?
        """, (coin_id, session['user_nif']))
        columns = [column[0] for column in cursor.description]
        coin = cursor.fetchone()
        
        if not coin:
            flash("Moeda não encontrada ou não pertence ao seu usuário", "warning")
            return redirect(url_for('list_all_coins'))
        
        # Converter para dicionário corretamente
        coin_dict = dict(zip(columns, coin))
        
        # Obter face nacional
        cursor.execute("""
            SELECT nf.*, a.Fname, a.Lname, a.Nacionality
            FROM [Numismatics].Nacional_Face nf
            LEFT JOIN [Numismatics].NF_Has_Author nfa ON nf.Coin_ID = nfa.Coin_ID AND nf.ID_Colection = nfa.ID_Colection
            LEFT JOIN [Numismatics].Author a ON nfa.NIF_Author_NF = a.NIF
            WHERE nf.Coin_ID = ? AND nf.ID_Colection = ?
        """, (coin_id, coin_dict['ColectionID']))
        nacional_face = [dict(zip([column[0] for column in cursor.description], row)) 
                        for row in cursor.fetchall()]
        
        # Obter face comum
        cursor.execute("""
            SELECT cf.*, a.Fname, a.Lname, a.Nacionality
            FROM [Numismatics].Common_Face cf
            LEFT JOIN [Numismatics].CF_Has_Author cfa ON cf.Coin_ID = cfa.Coin_ID AND cf.ID_Colection = cfa.ID_Colection
            LEFT JOIN [Numismatics].Author a ON cfa.NIF_Author_CF = a.NIF
            WHERE cf.Coin_ID = ? AND cf.ID_Colection = ?
        """, (coin_id, coin_dict['ColectionID']))
        common_face = [dict(zip([column[0] for column in cursor.description], row)) 
                      for row in cursor.fetchall()]
        
        # Obter informações do país e currency
        cursor.execute("""
            SELECT curr.Currency_Name, curr.ISO_Code, curr.Date_First_Edition, curr.Date_Last_Edition,
                   c.Number_Unique_CC_Coins
            FROM [Numismatics].Currency curr
            JOIN [Numismatics].Country c ON curr.Country_Name = c.Name
            WHERE curr.Country_Name = ?
        """, (coin_dict['Country_Name'],))
        currency_info = cursor.fetchone()
        currency_dict = dict(zip([column[0] for column in cursor.description], currency_info)) if currency_info else None
        
        return render_template('coin_details.html',
                            coin=coin_dict,
                            nacional_face=nacional_face,
                            common_face=common_face,
                            currency_info=currency_dict)
        
    except Exception as e:
        flash(f"Erro ao carregar detalhes da moeda: {str(e)}", "danger")
        return redirect(url_for('coins_list'))
    finally:
        conn.close()


    
#ADD COIN COM SP
@app.route('/add_coin', methods=['GET', 'POST'],endpoint='add_coin')
def add_coin():
    if 'user_nif' not in session:
        flash('You must be logged in to add a coin.', 'danger')
        return redirect(url_for('home'))
    
    nif_user = session['user_nif']
    
    if request.method == 'POST':
        # Capture form data
        value = request.form['value']
        country_name = request.form['country_name']
        other_country = request.form.get('other_country', None)
        collection_id = request.form['collection_id']
        mintage = request.form.get('mintage', None)
        condition = request.form['condition']
        material = request.form['material']
        finishing = request.form['finishing']
        market_price = request.form.get('market_price', 0.0)
        year = request.form['year']
        nf_description = request.form['nf_description']
        comemorative = request.form.get('comemorative', 0)
        mint_mark = request.form['mint_mark']
        cf_description = request.form['cf_description']
        cf_version = request.form['cf_version']

        # If the country is "Other", use the value from "other_country"
        if country_name == 'Other' and other_country:
            country_name = other_country

        conn = create_connection()
        cursor = conn.cursor()

        try:
            # Log the parameters being sent
            print("Executing stored procedure with parameters:")
            print(f"Value: {value}, Country: {country_name}, Mintage: {mintage}, Condition: {condition}, "
                  f"Material: {material}, Finishing: {finishing}, Market Price: {market_price}, "
                  f"Collection ID: {collection_id}, Year: {year}, NF Description: {nf_description}, "
                  f"Comemorative: {comemorative}, Mint Mark: {mint_mark}, CF Description: {cf_description}, "
                  f"CF Version: {cf_version}")

            # Execute the stored procedure
            cursor.execute("""
                DECLARE @Resultado BIT, @Mensagem VARCHAR(255);
                EXEC AdicionarMoeda 
                    @Value = ?, 
                    @Country_Name = ?, 
                    @Mintage = ?, 
                    @Condition = ?, 
                    @Material = ?, 
                    @Finishing = ?, 
                    @Market_Price = ?, 
                    @ID_Colection = ?, 
                    @Year = ?, 
                    @NF_Description = ?, 
                    @Comemorative = ?, 
                    @Mint_Mark = ?, 
                    @CF_Description = ?, 
                    @CF_Version = ?, 
                    @Resultado = @Resultado OUTPUT, 
                    @Mensagem = @Mensagem OUTPUT;
                SELECT @Resultado AS Resultado, @Mensagem AS Mensagem;
            """, (value, country_name, mintage, condition, material, finishing, market_price, collection_id, year, nf_description, comemorative, mint_mark, cf_description, cf_version))
            
            # Capture the result of the procedure
            result = cursor.fetchone()
            if result:
                # Access the result correctly
                resultado = result[0]  # Use index 0 for @Resultado
                mensagem = result[1]  # Use index 1 for @Mensagem

                if resultado:
                    flash(mensagem, 'success')
                else:
                    flash(mensagem, 'danger')
            else:
                flash('No result returned from the stored procedure.', 'danger')

            conn.commit()
        except Exception as e:
            conn.rollback()
            flash(f'Error adding coin: {str(e)}', 'danger')
        finally:
            conn.close()

        return redirect(url_for('ver_colecao', colecao_id=collection_id))



    # Load the user's collections
    conn = create_connection()
    cursor = conn.cursor()
    cursor.execute("SELECT ID FROM [Numismatics].Colection WHERE NIF_User = ?", (nif_user,))
    colecoes = [row[0] for row in cursor.fetchall()]
    conn.close()

    return render_template("add_coin.html",
                           conditions=list_conditions(),
                           finishings=list_finishings(),
                           materials=list_materials(),
                           colecoes=colecoes,
                           theme=BOOTSTRAP_THEME)


@app.route("/edit_coin/<int:coin_id>", methods=['GET', 'POST'])
def edit_coin(coin_id):
    if 'user_nif' not in session:
        flash('Por favor, faça login para editar moedas', 'danger')
        return redirect(url_for('login'))

    conn = create_connection()
    cursor = conn.cursor()

    if request.method == 'POST':
        value = request.form.get('value')
        mintage = request.form.get('mintage')
        condition = request.form.get('condition')
        material = request.form.get('material')
        finishing = request.form.get('finishing')
        market_price = request.form.get('market_price')
        country_name = request.form.get('country_name')

        try:
            sql = """
                DECLARE @Resultado BIT, @Mensagem VARCHAR(255);
                EXEC Edit_Coin @ID = ?, @New_Value = ?, @New_Mintage = ?, @New_Condition = ?,
                               @New_Material = ?, @New_Finishing = ?, @New_Market_Price = ?,
                               @New_Country_Name = ?, @Resultado = @Resultado OUTPUT, @Mensagem = @Mensagem OUTPUT;
                SELECT @Resultado, @Mensagem;
            """
            cursor.execute(sql, (coin_id, value, mintage, condition, material, finishing, market_price, country_name))
            row = cursor.fetchone()

            if row:
                resultado, mensagem = row[0], row[1]
                if resultado:
                    conn.commit()
                    flash(mensagem, 'success')
                else:
                    conn.rollback()
                    flash(mensagem, 'danger')
            else:
                conn.rollback()
                flash('Erro ao editar moeda.', 'danger')

        except pyodbc.Error as e:
            conn.rollback()
            flash(f'Erro no banco de dados: {str(e)}', 'danger')
        finally:
            conn.close()

        return redirect(url_for('list_all_coins'))

    # Se for GET, podes carregar os dados da moeda para preencher o formulário (opcional)
    return render_template("edit_coin.html")


#####################################
# eventos


@app.route('/eventos')
def eventos():
    if 'user_nif' not in session:
        flash('Por favor, faça login para acessar esta página', 'danger')
        return redirect(url_for('home'))
    
    conn = create_connection()
    cursor = conn.cursor()

    try:
        # Buscar todos os eventos
        cursor.execute("SELECT * FROM [Numismatics].Events")
        eventos = [dict(zip([column[0] for column in cursor.description], row)) 
                   for row in cursor.fetchall()]

        # Buscar todas as coleções do usuário
        cursor.execute("SELECT ID FROM [Numismatics].Colection WHERE NIF_User = ?", (session['user_nif'],))
        colecoes = [row[0] for row in cursor.fetchall()]

        eventos_participando = []
        eventos_participando_ids = set()

        # Usar a função fn_GetEventParticipation para cada coleção
        for colecao_id in colecoes:
            cursor.execute("""
                SELECT * FROM fn_GetEventParticipation(?, ?)
            """, (colecao_id, 0))  # 0 para incluir todos, não apenas futuros

            rows = cursor.fetchall()
            columns = [col[0] for col in cursor.description]
            for row in rows:
                evento = dict(zip(columns, row))
                if evento['EventID'] not in eventos_participando_ids:
                    eventos_participando_ids.add(evento['EventID'])
                    eventos_participando.append({
                        'ID': evento['EventID'],
                        'Name': evento['EventName'],
                        'Start_Date': evento['Start_Date'],
                        'End_Date': evento['End_Date'],
                        'Localization': evento['Localization']
                    })

        return render_template('eventos.html',
                               eventos=eventos,
                               eventos_participando=eventos_participando,
                               eventos_participando_ids=eventos_participando_ids)

    except Exception as e:
        flash(f"Erro ao carregar eventos: {str(e)}", "danger")
        return redirect(url_for('index'))

    finally:
        cursor.close()
        conn.close()


@app.route('/adicionar_evento', methods=['GET', 'POST'])
def adicionar_evento():
    if 'user_nif' not in session:
        return redirect(url_for('login'))
    
    if request.method == 'POST':
        nome = request.form.get('nome')
        data_inicio = request.form.get('data_inicio')
        data_fim = request.form.get('data_fim')
        localizacao = request.form.get('localizacao')
        
        if not all([nome, data_inicio, data_fim, localizacao]):
            flash("Por favor, preencha todos os campos", "danger")
            return redirect(url_for('adicionar_evento'))
        
        conn = create_connection()
        cursor = conn.cursor()
        
        try:
            # Chamar a stored procedure
            cursor.execute("""
                DECLARE @NewID INT;
                EXEC [Numismatics].AddEventWithParticipation 
                    @EventName = ?,
                    @StartDate = ?,
                    @EndDate = ?,
                    @Localization = ?,
                    @UserNIF = ?,
                    @NewEventID = @NewID OUTPUT;
                SELECT @NewID AS NewEventID;
            """, (nome, data_inicio, data_fim, localizacao, session['user_nif']))
            
            new_event_id = cursor.fetchone()[0]
            conn.commit()
            
            flash(f"Evento '{nome}' criado com sucesso e associado às suas coleções!", "success")
            return redirect(url_for('evento_details', event_id=new_event_id))
            
        except Exception as e:
            conn.rollback()
            flash(f"Erro ao criar evento: {str(e)}", "danger")
            return redirect(url_for('adicionar_evento'))
            
        finally:
            cursor.close()
            conn.close()
    
    return render_template('adicionar_evento.html')


@app.route('/join_event/<int:event_id>', methods=['POST'])
def join_event(event_id):
    if 'user_nif' not in session:
        return jsonify(success=False, message="User not logged in"), 401

    conn = None
    try:
        conn = create_connection()
        cursor = conn.cursor()
        
        # Get or create collection
        cursor.execute("SELECT ID FROM [Numismatics].Colection WHERE NIF_User = ?", (session['user_nif'],))
        collection = cursor.fetchone()
        
        if not collection:
            cursor.execute("""
                INSERT INTO [Numismatics].Colection (NIF_User, Creation_Date, Code_Type)
                VALUES (?, GETDATE(), 0)
            """, (session['user_nif'],))
            conn.commit()
            collection_id = cursor.lastrowid
        else:
            collection_id = collection[0]
        
        # Check if already participating
        cursor.execute("""
            SELECT 1 FROM [Numismatics].Participates_Event 
            WHERE ID_Colection = ? AND ID_Event = ?
        """, (collection_id, event_id))
        
        if cursor.fetchone():
            return jsonify(success=False, message="Already joined this event"), 400
        
        # Add participation
        cursor.execute("""
            INSERT INTO [Numismatics].Participates_Event (ID_Colection, ID_Event)
            VALUES (?, ?)
        """, (collection_id, event_id))
        conn.commit()
        
        return jsonify(success=True), 200

    except Exception as e:
        if conn:
            conn.rollback()
        app.logger.error(f"Error in join_event: {str(e)}")
        return jsonify(success=False, message=str(e)), 500

    finally:
        if conn:
            conn.close()



@app.route('/leave_event/<int:event_id>', methods=['POST'])
def leave_event(event_id):
    if 'user_nif' not in session:
        return jsonify(success=False, message="Usuário não está logado"), 401

    conn = create_connection()
    cursor = conn.cursor()
    
    try:
        # Encontrar a coleção do usuário
        cursor.execute("SELECT ID FROM [Numismatics].Colection WHERE NIF_User = ?", (session['user_nif'],))
        colecao = cursor.fetchone()
        
        if not colecao:
            return jsonify(success=False, message="Coleção não encontrada"), 400
        
        id_colecao = colecao[0]
        
        # Remover participação
        cursor.execute("""
            DELETE FROM [Numismatics].Participates_Event 
            WHERE ID_Colection = ? AND ID_Event = ?
        """, (id_colecao, event_id))
        conn.commit()
        
        return jsonify(success=True, message="Você saiu do evento com sucesso"), 200
    
    except Exception as e:
        conn.rollback()
        return jsonify(success=False, message=f"Erro ao sair do evento: {str(e)}"), 500
    
    finally:
        cursor.close()
        conn.close()


@app.route('/evento/<int:event_id>')
def evento_details(event_id):
    # Obter parâmetros da query string
    pais = request.args.get('pais', default=None, type=str)
    nacionalidade = request.args.get('nacionalidade', default=None, type=str)
    
    if 'user_nif' not in session:
        flash("Você precisa estar logado para ver detalhes de eventos", "warning")
        return redirect(url_for('login'))
    
    conn = create_connection()
    cursor = conn.cursor()
    
    try:
        cursor.execute("EXEC GetEventDetails ?", event_id)
        evento = cursor.fetchone()
        if not evento:
            flash("Evento não encontrado", "danger")
            return redirect(url_for('eventos'))
        evento = dict(zip([column[0] for column in cursor.description], evento))

        cursor.execute("EXEC CheckUserParticipation ?, ?", session['user_nif'], event_id)
        esta_participando = cursor.fetchone() is not None

        cursor.execute("select * From Numismatics.GetEventParticipants(?)", event_id)
        participantes = [dict(zip([column[0] for column in cursor.description], row)) for row in cursor.fetchall()]

        cursor.execute("select * From Numismatics.GetCountriesWithCommemorativeCoins()")
        paises_disponiveis = [row[0] for row in cursor.fetchall()]
        pais_escolhido = pais if pais and pais in paises_disponiveis else (paises_disponiveis[0] if paises_disponiveis else None)

        moedas_comemorativas = []
        if pais_escolhido:
            cursor.execute("select * From Numismatics.GetCommemorativeCoinsByCountryAndEvent(?, ?)", event_id, pais_escolhido)
            moedas_comemorativas = [dict(zip([column[0] for column in cursor.description], row)) for row in cursor.fetchall()]

        cursor.execute("select * from Numismatics.GetAvailableNationalities()")
        nacionalidades_disponiveis = [row[0] for row in cursor.fetchall()]
        nacionalidade_escolhida = nacionalidade if nacionalidade and nacionalidade in nacionalidades_disponiveis else (nacionalidades_disponiveis[0] if nacionalidades_disponiveis else None)

        autores = []
        if nacionalidade_escolhida:
            cursor.execute("select * from Numismatics.GetAuthorsByNationalityAndEvent(?, ?)", event_id, nacionalidade_escolhida)
            autores = [dict(zip([column[0] for column in cursor.description], row)) for row in cursor.fetchall()]

        cursor.execute("select * from Numismatics.GetCoinStatsByCondition(?)", event_id)
        moedas_por_condicao = [dict(zip([column[0] for column in cursor.description], row)) for row in cursor.fetchall()]

        
        return render_template('evento_details.html',
                            evento=evento,
                            esta_participando=esta_participando,
                            participantes=participantes,
                            pais_escolhido=pais_escolhido,
                            paises_disponiveis=paises_disponiveis,
                            moedas_comemorativas=moedas_comemorativas,
                            nacionalidade_escolhida=nacionalidade_escolhida,
                            nacionalidades_disponiveis=nacionalidades_disponiveis,
                            autores=autores,
                            moedas_por_condicao=moedas_por_condicao,
                            current_params={'pais': pais, 'nacionalidade': nacionalidade})
    
    except Exception as e:
        flash(f"Erro ao carregar detalhes do evento: {str(e)}", "danger")
        return redirect(url_for('eventos'))
    
    finally:
        cursor.close()
        conn.close()

###########################
@app.route('/Authors', methods=['GET', 'POST'])
def Authors():
    if 'user_nif' not in session:
        flash('Por favor, faça login para acessar esta página', 'danger')
        return redirect(url_for('home'))
    
    conn = create_connection()
    cursor = conn.cursor()
    
    try:
        filters = {
            'nif': request.form.get('nif', '').strip(),
            'nome': request.form.get('nome', '').strip(),
            'nacionalidade': request.form.get('nacionalidade', '').strip(),
            'idade': request.form.get('idade', '').strip(),
            'colecao_id': request.form.get('colecao_id', '').strip()
        }

        # Consulta base
        query = """
            SELECT DISTINCT a.*
            FROM [Numismatics].Author a
            WHERE 1=1
        """
        params = []

        # Aplicar filtros básicos
        if filters['nif']:
            query += " AND a.NIF LIKE ?"
            params.append(f'%{filters["nif"]}%')
            
        if filters['nome']:
            query += " AND (a.Fname LIKE ? OR a.Lname LIKE ?)"
            params.append(f'%{filters["nome"]}%')
            params.append(f'%{filters["nome"]}%')
            
        if filters['nacionalidade']:
            query += " AND a.Nacionality LIKE ?"
            params.append(f'%{filters["nacionalidade"]}%')

        # Filtro por coleção
        if filters['colecao_id']:
            query += """
                AND a.NIF IN (
                    SELECT NIF_Author_CF FROM [Numismatics].CF_Has_Author 
                    WHERE ID_Colection = ?
                    UNION
                    SELECT NIF_Author_NF FROM [Numismatics].NF_Has_Author 
                    WHERE ID_Colection = ?
                )
            """
            params.extend([filters['colecao_id'], filters['colecao_id']])

        cursor.execute(query, params)
        autores = [dict(zip([column[0] for column in cursor.description], row)) for row in cursor.fetchall()]

        # Adicionar idade para cada autor
        for autor in autores:
            cursor.execute("SELECT dbo.fn_GetAuthorAge(?) AS Age", (autor['NIF'],))
            result = cursor.fetchone()
            autor['Age'] = result.Age if result else None

        # Dados para filtros
        cursor.execute("SELECT DISTINCT Nacionality FROM [Numismatics].Author ORDER BY Nacionality")
        nacionalidades = [row[0] for row in cursor.fetchall()]

        # Obter coleções do usuário
        cursor.execute("SELECT ID, Creation_Date FROM [Numismatics].Colection WHERE NIF_User = ?", (session['user_nif'],))
        colecoes = [{'ID': row[0], 'Creation_Date': row[1]} for row in cursor.fetchall()]

        return render_template('authors.html', 
                           autores=autores,
                           nacionalidades=nacionalidades,
                           colecoes=colecoes,
                           filters=filters)

    except Exception as e:
        flash(f"Erro ao carregar autores: {str(e)}", "danger")
        return redirect(url_for('index'))
    
    finally:
        cursor.close()
        conn.close()
 

@app.route('/author/<int:nif>')
def author_details(nif):
    if 'user_nif' not in session:
        return redirect(url_for('login'))
    
    conn = create_connection()
    cursor = conn.cursor()
    
    try:
        # Informações básicas do autor
        cursor.execute("SELECT * FROM [Numismatics].Author WHERE NIF = ?", (nif,))
        autor = cursor.fetchone()
        
        if not autor:
            flash("Autor não encontrado.", "danger")
            return redirect(url_for('Authors'))
        
        autor = dict(zip([column[0] for column in cursor.description], autor))
        
        # Calcular idade
        cursor.execute("SELECT dbo.fn_GetAuthorAge(?) AS Age", (nif,))
        autor['Age'] = cursor.fetchone().Age
        
        # Faces comuns associadas
        cursor.execute("""
            SELECT cf.Coin_ID, cf.Description, cf.Version, c.ID_Colection, col.Creation_Date
            FROM [Numismatics].Common_Face cf
            JOIN [Numismatics].CF_Has_Author cfa ON cf.Coin_ID = cfa.Coin_ID AND cf.Description = cfa.Description AND cf.ID_Colection = cfa.ID_Colection
            JOIN [Numismatics].Coin c ON cf.Coin_ID = c.ID AND cf.ID_Colection = c.ID_Colection
            JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
            WHERE cfa.NIF_Author_CF = ?
        """, (nif,))
        faces_comuns = [dict(zip([column[0] for column in cursor.description], row)) for row in cursor.fetchall()]
        
        # Faces nacionais associadas
        cursor.execute("""
            SELECT nf.Coin_ID, nf.Description, nf.Year, nf.Comemorative, nf.Mint_Mark, c.ID_Colection, col.Creation_Date
            FROM [Numismatics].Nacional_Face nf
            JOIN [Numismatics].NF_Has_Author nfa ON nf.Coin_ID = nfa.Coin_ID AND nf.Description = nfa.Description AND nf.ID_Colection = nfa.ID_Colection
            JOIN [Numismatics].Coin c ON nf.Coin_ID = c.ID AND nf.ID_Colection = c.ID_Colection
            JOIN [Numismatics].Colection col ON c.ID_Colection = col.ID
            WHERE nfa.NIF_Author_NF = ?
        """, (nif,))
        faces_nacionais = [dict(zip([column[0] for column in cursor.description], row)) for row in cursor.fetchall()]
        
        # Coleções onde o autor tem moedas
        colecoes_ids = set()
        for face in faces_comuns + faces_nacionais:
            colecoes_ids.add(face['ID_Colection'])
        
        colecoes = []
        if colecoes_ids:
            placeholders = ','.join(['?']*len(colecoes_ids))
            cursor.execute(f"SELECT ID, Creation_Date FROM [Numismatics].Colection WHERE ID IN ({placeholders})", tuple(colecoes_ids))
            colecoes = [dict(zip(['ID', 'Creation_Date'], row)) for row in cursor.fetchall()]
        
        return render_template('author_details.html', 
                            autor=autor,
                            faces_comuns=faces_comuns,
                            faces_nacionais=faces_nacionais,
                            colecoes=colecoes)
    
    except Exception as e:
        flash(f"Erro ao carregar detalhes do autor: {str(e)}", "danger")
        return redirect(url_for('Authors'))
    
    finally:
        cursor.close()
        conn.close()

# Adicione este filtro personalizado antes das rotas
@app.template_filter('enumerate')
def jinja2_enumerate(iterable, start=0):
    return enumerate(iterable, start)

# 1. Moedas com data de primeira edição maior que o ano da moeda
@app.route("/moedas_inconsistentes")
def moedas_inconsistentes():
    conn = create_connection()
    cursor = conn.cursor()
    
    query = """
    SELECT 
        Coin.ID,
        Currency.Date_First_Edition,
        Currency.Currency_Name,
        NF.[Year]
    FROM 
        [Numismatics].Coin
    JOIN [Numismatics].Currency ON Currency.Country_Name = Coin.Country_Name
    JOIN [Numismatics].Nacional_Face NF ON NF.Coin_ID = Coin.ID
    WHERE 
        Currency.Date_First_Edition > NF.[Year]
    """
    
    cursor.execute(query)
    moedas = cursor.fetchall()
    conn.close()
    
    return render_template('moedas_inconsistentes.html', 
                         moedas=moedas,
                         theme=BOOTSTRAP_THEME)

# 2. Listar moedas por coleção de um user
@app.route("/user_coins/<int:nif_user>")
def user_coins(nif_user):
    conn = create_connection()
    cursor = conn.cursor()
    
    query = """
    SELECT 
        Coin.ID,
        Coin.ID_Colection,
        Coin.Country_Name,
        Coin.Material,
        Coin.[Condition],
        Coin.[Value],
        Coin.Mintage,
        Coin.Market_Price
    FROM [Numismatics].Coin
    JOIN [Numismatics].Colection ON Coin.ID_Colection = Colection.ID
    WHERE Colection.NIF_User = ?
    """
    
    cursor.execute(query, (nif_user,))
    coins = cursor.fetchall()
    conn.close()
    
    return render_template('user_coins.html', 
                         moedas=coins,
                         nif_user=nif_user,
                         theme=BOOTSTRAP_THEME) 

# 3. Users que participam em eventos
@app.route("/users_em_eventos")
def users_em_eventos():
    conn = create_connection()
    cursor = conn.cursor()
    
    query = """
    SELECT DISTINCT 
    
        [User].NIF,
        [User].Fname,
        [User].Lname,
        [User].Email
    FROM [Numismatics].[User]
    JOIN [Numismatics].Colection ON [User].NIF = Colection.NIF_User
    JOIN [Numismatics].Participates_Event ON Colection.ID = Participates_Event.ID_Colection
    """
    
    cursor.execute(query)
    users = cursor.fetchall()
    conn.close()
    
    return render_template('users_em_eventos.html', 
                         users=users,
                         theme=BOOTSTRAP_THEME)

# 4. Coleções por user
@app.route("/colecoes_user/<int:nif_user>")
def colecoes_user(nif_user):
    conn = create_connection()
    cursor = conn.cursor()
    
    query = """
    SELECT 
        Colection.ID,
        Colection.Creation_Date,
        [Type].Designation
    FROM [Numismatics].Colection
    JOIN [Numismatics].[Type] ON Colection.Code_Type = [Type].Code_Type
    WHERE Colection.NIF_User = ?
    """
    
    cursor.execute(query, (nif_user,))
    colecoes = cursor.fetchall()
    conn.close()
    
    return render_template('colecoes_user.html', 
                         colecoes=colecoes,
                         nif_user=nif_user,
                         theme=BOOTSTRAP_THEME)



########## 
# add
#########



# Add Author
@app.route('/add_author', methods=['GET', 'POST'])
def add_author():
    if request.method == 'POST':
        nif = request.form['nif']
        fname = request.form['fname']
        lname = request.form['lname']
        nacionality = request.form['nacionality']
        bdate = request.form['bdate']
        
        conn = create_connection()
        cursor = conn.cursor()
        
        try:
            cursor.execute("""
                IF NOT EXISTS (SELECT 1 FROM Author WHERE NIF = ?)
                BEGIN
                    INSERT INTO [Numismatics].Author (NIF, Fname, Lname, Nacionality, BDate)
                    VALUES (?, ?, ?, ?, ?)
                END
            """, (nif, nif, fname, lname, nacionality, bdate))
            conn.commit()
            flash('Author added successfully!', 'success')
        except Exception as e:
            conn.rollback()
            flash(f'Error adding author: {str(e)}', 'danger')
        finally:
            conn.close()
        
        return render_template('add_author.html')
    
    return render_template('add_author.html')

# Add Event


# Add Common Face
@app.route('/add_common_face', methods=['GET', 'POST'])
def add_common_face():
    conn = create_connection()
    cursor = conn.cursor()
    
    if request.method == 'POST':
        coin_id = request.form['coin_id']
        description = request.form['description']
        version = request.form['version']
        
        try:
            cursor.execute("""
                IF NOT EXISTS (SELECT 1 FROM [Numismatics].Common_Face WHERE Coin_ID = ?)
                BEGIN
                    INSERT INTO [Numismatics].Common_Face (Coin_ID, [Description], [Version])
                    VALUES (?, ?, ?)
                END
            """, (coin_id, coin_id, description, version))
            
            conn.commit()
            flash('Common face added successfully!', 'success')
        except Exception as e:
            conn.rollback()
            flash(f'Error adding common face: {str(e)}', 'danger')
        finally:
            conn.close()
        
        return render_template('add_common_face.html', coins=coins)
    
    # Get coins for dropdown
    cursor.execute("SELECT ID FROM Coin")
    coins = cursor.fetchall()
    conn.close()
    
    return render_template('add_common_face.html', coins=coins)

# Add National Face
@app.route('/add_national_face', methods=['GET', 'POST'])
def add_national_face():
    conn = create_connection()
    cursor = conn.cursor()
    
    if request.method == 'POST':
        coin_id = request.form['coin_id']
        year = request.form['year']
        description = request.form['description']
        comemorative = 1 if 'comemorative' in request.form else 0
        mint_mark = request.form['mint_mark']
        
        try:
            cursor.execute("""
                IF NOT EXISTS (SELECT 1 FROM [Numismatics].Nacional_Face WHERE Coin_ID = ?)
                BEGIN
                    INSERT INTO [Numismatics].Nacional_Face (Coin_ID, [Year], [Description], Comemorative, Mint_Mark)
                    VALUES (?, ?, ?, ?, ?)
                END
            """, (coin_id, coin_id, year, description, comemorative, mint_mark))
            
            conn.commit()
            flash('National face added successfully!', 'success')
        except Exception as e:
            conn.rollback()
            flash(f'Error adding national face: {str(e)}', 'danger')
        finally:
            conn.close()
        
        return render_template('add_national_face.html', coins=coins)
    
    # Get coins for dropdown
    cursor.execute("SELECT ID FROM [Numismatics].Coin")
    coins = cursor.fetchall()
    conn.close()
    
    return render_template('add_national_face.html', coins=coins)

# Add User
@app.route('/add_user', methods=['GET', 'POST'])
def add_user():
    if request.method == 'POST':
        nif = request.form['nif']
        fname = request.form['fname']
        lname = request.form['lname']
        email = request.form['email']
        bdate = request.form['bdate']
        phone = request.form['phone']
        
        conn = create_connection()
        cursor = conn.cursor()
        
        try:
            cursor.execute("""
                IF NOT EXISTS (SELECT 1 FROM [Numismatics].[User] WHERE NIF = ?)
                BEGIN
                    INSERT INTO [Numismatics].[User] (NIF, Fname, Lname, Email, BDate, Phone)
                    VALUES (?, ?, ?, ?, ?, ?)
                END
            """, (nif, nif, fname, lname, email, bdate, phone))
            conn.commit()
            flash('User added successfully!', 'success')
        except Exception as e:
            conn.rollback()
            flash(f'Error adding user: {str(e)}', 'danger')
        finally:
            conn.close()
        
        return render_template('add_user.html')
    
    return render_template('add_user.html')

# Add Collection

@app.route('/add_collection', methods=['GET', 'POST'])
def add_collection():
    if 'user_nif' not in session:
        flash('Por favor, faça login para adicionar coleções', 'danger')
        return redirect(url_for('login'))

    conn = create_connection()
    cursor = conn.cursor()

    try:
        # Obter tipos de coleção disponíveis
        cursor.execute("SELECT Code_Type, Designation FROM [Numismatics].[Type]")
        types = cursor.fetchall()

        if request.method == 'POST':
            # Usar o NIF diretamente da sessão
            nif_user = session['user_nif']
            code_type = request.form['code_type']
            creation_date = request.form['creation_date']

            # Inserir a nova coleção
            cursor.execute("""
                INSERT INTO [Numismatics].Colection (NIF_User, Creation_Date, Code_Type)
                VALUES (?, ?, ?)
            """, (nif_user, creation_date, code_type))

            conn.commit()
            flash('Coleção adicionada com sucesso!', 'success')
            return redirect(url_for('colecoes'))

        # Para GET, mostrar formulário
        return render_template('add_collection.html', 
                            current_user_nif=session['user_nif'],
                            types=types)

    except Exception as e:
        conn.rollback()
        flash(f'Erro ao adicionar coleção: {str(e)}', 'danger')
        return redirect(url_for('add_collection'))
    finally:
        conn.close()



@app.route("/delete_coin", methods=['POST'])
def delete_coin():
    if 'user_nif' not in session:
        flash('Você precisa estar logado para deletar moedas.', 'danger')
        return redirect(url_for('home'))

    coin_id = int(request.form.get('coin_id'))
    collection_id = int(request.form.get('collection_id'))


    conn = create_connection()
    cursor = conn.cursor()

    try:
        # Verifica se a moeda pertence ao usuário antes de deletar
        cursor.execute("""
            SELECT C.ID 
            FROM [Numismatics].Coin C
            JOIN [Numismatics].Colection Col ON C.ID_Colection = Col.ID
            WHERE C.ID = ? AND Col.NIF_User = ? AND Col.ID = ?
        """, (coin_id, session['user_nif'], collection_id))
        result = cursor.fetchone()

        if not result:
            flash("Moeda não encontrada ou você não tem permissão para deletá-la.", "warning")
            return redirect(url_for('user_profile'))

        # Executa a stored procedure de remoção
        cursor.execute("""
            EXEC RemoveCoin 
                @CoinID = ?, 
                @CollectionID = ?
        """, (coin_id, collection_id))
        
        conn.commit()
        flash("Moeda removida com sucesso.", "success")
    except Exception as e:
        conn.rollback()
        flash(f"Erro ao remover moeda: {str(e)}", "danger")
    finally:
        conn.close()

    return redirect(url_for('ver_colecao', colecao_id=collection_id))

@app.route('/delete_collection', methods=['POST'])
def delete_collection():
    if 'user_nif' not in session:
        flash('Você precisa estar logado para realizar essa ação.', 'danger')
        return redirect(url_for('home'))

    collection_id = request.form['collection_id']
    confirmation_number = request.form['confirmation_number']

    if collection_id != confirmation_number:
        flash('Número de confirmação incorreto.', 'danger')
        return redirect(url_for('colecoes'))

    try:
        conn = create_connection()
        cur = conn.cursor()

        collection_id_int = int(collection_id)

        # Apagar autores das faces nacionais
        cur.execute("""
            DELETE CFA
            FROM [Numismatics].CF_Has_Author CFA
            JOIN [Numismatics].Common_Face CF ON CFA.Coin_ID = CF.Coin_ID AND CFA.[Description] = CF.[Description] AND CFA.ID_Colection = CF.ID_Colection
            WHERE CFA.ID_Colection = ?
        """, (collection_id_int,))
        
        cur.execute("""
            DELETE NFA
            FROM [Numismatics].NF_Has_Author NFA
            JOIN [Numismatics].Nacional_Face NF ON NFA.Coin_ID = NF.Coin_ID AND NFA.[Description] = NF.[Description] AND NFA.ID_Colection = NF.ID_Colection
            WHERE NFA.ID_Colection = ?
        """, (collection_id_int,))

        # Apagar faces comuns e nacionais
        cur.execute("""
            DELETE CF
            FROM [Numismatics].Common_Face CF
            JOIN [Numismatics].Coin C ON CF.Coin_ID = C.ID AND CF.ID_Colection = C.ID_Colection
            WHERE CF.ID_Colection = ?
        """, (collection_id_int,))
        
        cur.execute("""
            DELETE NF
            FROM [Numismatics].Nacional_Face NF
            JOIN [Numismatics].Coin C ON NF.Coin_ID = C.ID AND NF.ID_Colection = C.ID_Colection
            WHERE NF.ID_Colection = ?
        """, (collection_id_int,))

        # Apagar participações em eventos
        cur.execute("DELETE FROM [Numismatics].Participates_Event WHERE ID_Colection = ?", (collection_id_int,))

        # Apagar moedas
        cur.execute("DELETE FROM [Numismatics].Coin WHERE ID_Colection = ?", (collection_id_int,))

        # Finalmente apagar a coleção
        cur.execute("DELETE FROM [Numismatics].Colection WHERE ID = ?", (collection_id_int,))

        conn.commit()

        flash('Coleção removida com sucesso!', 'success')
    except Exception as e:
        print("Erro ao apagar:", e)
        flash('Erro ao remover a coleção.', 'danger')
    finally:
        if conn:
            cur.close()
            conn.close()

    return redirect(url_for('colecoes'))





if __name__ == "__main__":
    app.run(debug=True)