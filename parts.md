# Pay2Play

## Concepte

vu le gros nombre de follower sur twitch, il y a forcement un nombre qui aura envie de jouer avec son streamer préféré !

Le but est de mettre en lien ces deux dernier avec un system payant automatiser, et en tirer une commission avec un systeme de session.

Le systeme de session est un systeme qui permettra a un follower de choisir son streamer en fonction du prix, dispo .. etc (a développer)

[TODO -> ajouter + de def] 

## BDD

### tables 
* user
    * global info :
        1. id_user
        2. username
        3. nickename
        4. email
        5. pw
        6. linked to twitch or discord (a voir comment c'est gere ce truc)
        7. id_session dont ce user est contribuer 
        8. nb_de_session deja effectuée


                
    * user 1 (streamer)   
                    comment retirer les info de twitch ? (api ??) pense pas ca donne acces a l'info des users ou ptetre dans un premier temps le user renseigne un nombre approximative de ses followers ??
                    nb_followers
                    
    * user 2 (follower)

    * user 3 (admin)

                   
        
* Session
    1. id session
    2. duration
    3. price
    4. game
    5. id du user 1 (streamer) [1->1]
    6. id du user 2 (follower) [1->1]
    7. type de session (en cours, non validé, annulé, supprimé.. etc) pour garder l'etat actuelle des sessions dans le cas du non finition de l'usage, où on lui permettra d'écrire se remettre sur le formulaire de ces session son tout retaper


## Maquette

* home page with all the stuff (contact about etc..)
login page
* a page for followers with a list of streamers filtred with prices, games, disponibilities.. etc
* une page pour les follower pour "creer une nouvelle session" (garder)
* contact us 
* about us
    



