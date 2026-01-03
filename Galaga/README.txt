JRIRIA ATIGUI Abir 
LOGMO TONDI Victoria  
Groupe : Défi


Compilation et exécution : 

Normalement sur les PC Linux de l'université nous pouvons compiler et exécuter directement à l'aide du bouton Run Code sur VSC depuis le fichier App.
Par contre, si ça marche pas essayer la méthode ci-dessous que nous avons utilisé sur les PC personnels  Windows : 

1. Ouvrir un terminal dans le dossier `Galaga/src/`
Il faut être sûr que le répértoire courant est le src.
Si on met la commande "ls" nous devrions voir les répértoires :   engine    game 

2. Compiler le projet :
   Mettre la commande suivant pour compiler :
   ```bash
   javac engine/*.java game/*.java game/actors/Base/*.java game/actors/Monsters/*.java game/actors/Zones/*.java
   ```

3. Exécuter le jeu :
   ```bash
   java engine.App
   ```


Contrôles :
- Flèche gauche / Flèche droite : déplacer le vaisseau.
- Espace : tirer un missile.
- 1 / 2 : (au démarrage) sélectionner le niveau.
- i : activation/désactivation du mode vies infinies. 
Celle-ci est importante de la garder en tête si on veut l'utiliser car nous n'avons pas marqué cette fonctionnalité directement dans le jeu ! (ce mode nous a simplifié la vérification du fonctionnement
du jeu sans mourir trop vite pendant le développent du jeu)
- Espace (après game over/avant partie) : retourner à la sélection de niveau/ débuter partie.



Mécaniques principales :

1. Système vies : 3 vies pour le joueur. Si un Moth vole une vie, on peut la récupérer après l'avoir tué.

2. Formation : Les monstres se déplacent en formation de droite à gauche.
Une fois que l'un des monstres touche l'un des murs, la direction de toute la formation change.

3. Attaques : Aléatoirement, les monstres étant à l'avant de la formation, quittent la formation et attaquent.
La formation a un temps de cooldown entre deux attaques de deux monstres dépendant du niveau dans lequel nous sommes.
Pour le niveau 1 c'est 4000 ms, donc 4s minimum.

4.Types de monstres : Bee (attaque en zig zag), Butterfly (descente en ligne droite) et le Moth (capture le vaisseau puis remonte).

5. Attaque du Moth (plus complexe) : Descend en suivant le joueur, si il réussit à avoir le joueur en dessous de lui ne serait-ce qu'une seconde,
il capture le vaisseau (et donc vole une vie) avant de remonter en ligne droite et se resynchronyse avec la formation un peu au dessus.
Pour récuperer le vaisseau volé, il suffit de tuer le Moth l'ayant volé et ainsi une vie nous sera rendue.

6. Missiles : Le joueur tire vers le haut, tandis que les monstres tirs vers le bas.
Le joueur n'a le droit qu'à 3 missiles maximum présents sur l'écran, parcontre les monstres n'ont pas de limite à part un cooldown individuel
à chaque monstre, qui encore une fois dépend du niveau dans lequel nous sommes.
Les missiles restent toujours dans la partie de jeu du milieu, elle ne dépasse pas sur la zone d'information ni la zone de score.
De plus, lorsqu'un missile sort de cette zone, il est supprimé pour laisser de la place au nouveaux crées.

7. Collisions :
   - La collision entre le joueur et un missile ennemi provoque la perte d'une vie.
   Lorsque cela se produit il y a deux scénarios. Soit le joueur a encore des vies et peut continuer à jouer,
   donc lui et tous les monstres encore en vie réaparaîssent en formation initiale après un compte à rebours.
   Soit le joueur n'a plus de vie et donc termine la partie par un GAME OVER.
   - La collision entre le joueur et un autre monstre provoque la perte d'une vie également.
   Par contre, le monstre est aussi tué, donc si nous sommes dans le cas où le joueur a encore de la vie,
   alors la valeur du monstre mort nous est ajouté au score de la partie actuelle.

8. Niveaux : 2 niveaux avec configurations différentes, chargées depuis les fichiers correspondants.

9. Meilleur score : Sauvegardé en fichier et chargé au démarrage.
S'affiche en haut de l'écran et également à la fin d'une partie dans laquelle nous avons obtenu un nouveau record !

10. Mode vies infinies : s'active et se désactive à l'aide de la touche I sur le clavier.