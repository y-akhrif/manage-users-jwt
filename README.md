# manage-users-jwt
To test the API you can use swagger by accessing this link http://localhost:9090/swagger-ui.html after compiling and starting the application.

## 1 - Génération d'utilisateurs
method: GET
url: /api/users/generate
content-type: application/json
secured: no
parameters: 
- count: number
Cet endpoint REST permet de générer un fichier json contenant count éléments en respectant la structure suivante:

[{ 
  "firstName": "string",
  "lastName": "string",
  "birthDate": "date",
  "city": "ville",
  "country": "code iso2",
  "avatar": "url d'une image",
  "company": "string",
  "jobPosition": "string",
  "mobile": "numéro de téléphone",
  "username": "identifiant de connexion",
  "email": "adresse email",
  "password": "mot de passe alétoire entre 6 et 10 caractères",
  "role": "admin ou user"
}]

Tous les champs de ce fichier JSON doivent sont générés de façon à avoir des résultants vraisemblables en utilisant une librairie Java appropriée. 

Le champs role doit etre généré en choisissant parmi les deux valeurs role ou admin

En mettant l'URL de cette API dans le navigateur (ex: http://localhost:9090/api/users/generate?count=100 le téléchargement d'un fichier JSON doit être déclenché. Le JSON ne doit pas être affiché sous forme texte dans le navigateur web.
