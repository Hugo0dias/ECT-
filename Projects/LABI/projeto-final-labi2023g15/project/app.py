import os.path
import cherrypy
import csv
import sqlite3 as sql
import hashlib
import time
import json
#from flask import render_template

cherrypy.config.update({'server.socket_port': 10015,})

# O caminho absoluto para o diretório base deste arquivo
baseDir = os.path.abspath(os.path.dirname(__file__))

# Dictionary with this application's static directories configuration
config = {
    "/": { "tools.staticdir.root": baseDir },
    "/html": { "tools.staticdir.on": True, "tools.staticdir.dir": "html" },
    "/js": { "tools.staticdir.on": True, "tools.staticdir.dir": "js" },
    "/css": { "tools.staticdir.on": True, "tools.staticdir.dir": "css" },
    "/images": { "tools.staticdir.on": True, "tools.staticdir.dir": "images" },
    "/uploads": { "tools.staticdir.on": True, "tools.staticdir.dir": "uploads" },

}

class Root(object):
    @cherrypy.expose
    def index(self):
        return open("html/login.html") 

    # UpLoad image
    @cherrypy.expose
    def upload(self, myFile, nameImg, authorImg):
        print("nome imagem: ", nameImg)
        print("nome autor: ", authorImg)

        h = hashlib.sha256()

        print("myfile: ", myFile)
        print("self: ", self)

        filename = baseDir + "/uploads/" + myFile.filename
        fileout = open(filename, "wb")
        while True:
            data = myFile.file.read(8192)
            if not data:
                break
            fileout.write(data)
            h.update(data)
        fileout.close()

        ext = myFile.filename.split(".")[-1]
        # final path of the image and changing the filename
        path = "uploads/" + h.hexdigest() + "." + ext
        #path = "uploads/" + filename + "." + ext
        os.rename(filename, path)
        
        # nameImg and authorImg are input parameters of this method
        # obtain the date and time of loading
        datetime = time.strftime('date:%d-%m-%Y time:%H:%M:%S')

        # insert the file information in the images table
        # eventually initialize the votes tables

        # new implementation base de dados
        db = sql.connect('database.db')

        #db.execute("CREATE TABLE images (id INTEGER PRIMARY KEY AUTOINCREMENT, nameImg TEXT, authorImg TEXT, path TEXT, datetime TEXT)") #criar tabela images
        #db.execute("CREATE TABLE comments (id INTEGER PRIMARY KEY AUTOINCREMENT, idimg INTEGER, user TEXT, comment TEXT, datetime TEXT)") #criar tabela comments
        #db.execute("CREATE TABLE votes (id INTEGER PRIMARY KEY AUTOINCREMENT, idimg INTEGER, ups INTEGER, downs INTEGER)") #criar tabela votes

        db.execute("INSERT INTO images (nameImg, authorImg, path, datetime) VALUES (?, ?, ?, ?)", (nameImg, authorImg, path, datetime))

        #idimg = db.execute("SELECT id FROM images WHERE path = ?", (path,))

        #db.execute("INSERT INTO votes (idimg, ups, downs) VALUES (?, ?, ?)", (idimg, "1", "1"))
        #result = db.execute("SELECT path FROM images") mostrar nome foto original


        result = db.execute("SELECT * FROM images")
        rows = result.fetchall()
        
        for row in rows:
            print("T:", row)
        
        db.commit()
        db.close()

    # List requested images
    @cherrypy.expose #new code -----------------------------------------
    def list(self, id):
        db = sql.connect('database.db')
        if (id == "all"):
            result = db.execute("SELECT id, nameImg, authorImg, path, datetime FROM images")  # Consulta para todas as imagens
        else:
            result = db.execute("SELECT id, nameImg, authorImg, path, datetime FROM images WHERE authorImg = ?", (id,))  # Consulta para imagens de um autor específico
        rows = result.fetchall()

        # Generate result (list of dictionaries) from rows (list of tuples)
        result = []
        for row in rows:
            image = {
                'id': row[0],
                'name': row[1],
                'author': row[2],
                'path': "/" + row[3],
                'datetime': row[4]
            }
            result.append(image)
            #print("result: ", result)
        db.close()
        # Sort result by image name
        sorted_images = sorted(result, key=lambda x: x['name'])

        cherrypy.response.headers["Content-Type"] = "application/json"
        return json.dumps({"images": sorted_images}).encode("utf-8")

#-------------------------------------------
    # List comments
    @cherrypy.expose
    def upvote(self, idimg):
        print("IDLIKE: ", idimg)
        db = sql.connect('database.db')
        
        result = db.execute("UPDATE votes SET ups = ups + 1 WHERE idimg = ?", (idimg))
        db.commit()
        db.close()

    @cherrypy.expose
    def downvote(self, idimg):
        print("IDLIKE: ", idimg)
        db = sql.connect('database.db')
        
        result = db.execute("UPDATE votes SET downs = downs + 1 WHERE idimg = ?", (idimg))
        db.commit()
        db.close()

    @cherrypy.expose
    def newcomment(self, idimg, username, newcomment):
        print(username)
        print(newcomment)
        print("------------------new---------------")
        db = sql.connect('database.db')
        datetime = time.strftime('date:%d-%m-%Y time:%H:%M:%S')

        db.execute("INSERT INTO comments (idimg, user, comment, datetime) VALUES (?, ?, ?, ?)", (idimg, username, newcomment, datetime))
        result = db.execute("SELECT * FROM comments WHERE idimg = ?", (idimg,))
        db.commit()
        db.close()


    @cherrypy.expose
    def comments(self, idimg):
        db = sql.connect('database.db')
        print("IDIMG: ", idimg)
        
        # result = db.execute(query of type SELECT for image of the id idimg)
        result = db.execute("SELECT id, nameImg, authorImg, path, datetime FROM images WHERE id = ?", (idimg,))
        row = result.fetchone()
    
        # Generate output dictionary with image information
        imageinfo = dict()
        imageinfo = {
            'id': row[0],
            'name': row[1],
            'author': row[2],
            'path': "/" + row[3],
            'datetime': row[4]
        }

        # Generate output dictionary with image comments list ----
        
        result = db.execute("SELECT * FROM comments WHERE idimg = ?", (idimg,))
        comments = []
        rows = result.fetchall()
        for row in rows:
            comment = {
                'idimg': row[1],
                'user': row[2],
                'comment': row[3],
                'datetime': row[4]
            }
            comments.append(comment)


        
        #result = db.execute("SELECT id, idimg, user, comment, datetime FROM comments WHERE id = ?", (idimg,))


        # result = db.execute(query of type SELECT for votes of the id idimg)

        #row = result.fetchone()
        #db.close()

        # Generate output dictionary with image votes
        #result = db.execute("SELECT ups, downs FROM votes WHERE idimg = ?", (idimg,))
        #rows = result.fetchall()
        #for row in rows:
        #    print(row)
        #    imagevotes = {
        #        "thumbs_up": row[1],
        #        "thumbs_down": row[2]
        #    }
        

        db.close()


        # Gerar dicionário de saída com informações de votos
        imagevotes = dict()
        imagevotes["thumbs_up"] = 0
        imagevotes["thumbs_down"] = 0
        #db.close()

        cherrypy.response.headers["Content-Type"] = "application/json"
        return json.dumps({"image": imageinfo, "comments": comments, "votes": imagevotes}).encode("utf-8")



class Actions(object):
    #ficheiro de logins/create accounts
    def update_file(self, username, password):
        data = [[username, password]]

        with open("contas.csv", "a", newline="") as csvfile:
            writer = csv.writer(csvfile)
            writer.writerows(data)  
    
    def is_registered(self, username, password):
        with open("contas.csv", "r") as csvfile:
            reader = csv.reader(csvfile)
            for elements in reader:
                if elements is None:
                    return False
                elif len(elements) >= 2 and elements[0] == username and elements[1] == password:
                    print(elements[0])
                    print(elements[1])
                    return True
        return False

    @cherrypy.expose
    def doCreateAccount(self, username=None, password=None):
        print(username)
        print(password)
        if username != None and password != None:
            if self.is_registered(username, password):
                print("Conta existente. Faça Login!") #mensagem no site
                
            else:
                self.update_file(username, password)
                print(username)
                print(password)
                return open("html/login.html")
                #if self.is_registered(username, password):
                    #return open("html/index.html").read()
        else:
            return "Por favor, preencha todos os campos."


    @cherrypy.expose
    def doLogin(self, username=None, password=None):
        print("login:", username)
        print("login:", password)
        if self.is_registered(username, password):
            #enviar o nome do username para o index
            #render_template("index.html", nome=username)
            return open("html/index.html")
        else:
            print("Dados invalidos")




root = Root()
root.actions = Actions()

cherrypy.quickstart(root, "/", config)
