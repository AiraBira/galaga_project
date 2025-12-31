JRIRIA ATIGUI Abir 
LOGMO TONDI Victoria  
Groupe: Défi


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
- **Flèche gauche / Flèche droite** : déplacer le vaisseau.
- **Espace** : tirer un missile.
- **'1' / '2'** : (au démarrage) sélectionner le niveau.
- **'i'** : activation/désactivation du mode vies infinies. 
Celle-ci est importante de la garder en tête si on veut l'utiliser car nous n'avons pas marqué cette fonctionnalité directement dans le jeu ! (ce mode nous a simplifié la vérification du fonctionnement
du jeu sans mourir trop vite pendant le développent du jeu)
- **Espace** (après game over/avant partie) : retourner à la sélection de niveau/ débuter partie.

Mécaniques principales :
1.**Système de vies** 3 vies pour le joueur. Si un Moth vole une vie, on peut la récupérer après l'avoir tué
2. **Formation** : Les monstres se déplacent en formation de droite à gauche. Une fois que l'un des monstres touche l'un des murs, la direction de toute la formation change.
3. **Attaques** : Aléatoirement, les monstres étant à l'avant de la formation, quittent la formation et attaquent. La formation a un temps de cooldown entre deux attaques de deux monstres dépendant du niveau dans lequel nous sommes. Pour le niveau 1 c'est 4000 ms, donc 4s minimum.
4.**Types de monstres** : Bee (attaque en zig zag), Butterfly (descente en ligne droite) et le Moth (capture le vaisseau puis remonte).
5. **Attaque du Moth (plus complexe)** : Descend en suivant le joueur, si il réussit à avoir le joueur en dessous de lui ne serait-ce qu'une seconde, il capture le vaisseau (et donc vole une vie) avant de remonter en ligne droite et se resynchronyse avec la formation un peu au dessus.
Pour récuperer le vaisseau volé, il suffit de tuer le Moth l'ayant volé et ainsi une vie nous sera rendue.
6. **Missiles** : Le joueur tire vers le haut, tandis que les monstres tirs vers le bas.
Le joueur n'a le droit qu'à 3 missiles maximum présents sur l'écran, parcontre les monstres n'ont pas de limite à part un cooldown individuel à chaque monstre, qui encore une fois dépend du niveau dans lequel nous sommes.
Les missiles restent toujours dans la partie de jeu du milieu, elle ne dépasse pas sur la zone d'information ni la zone de score. De plus, lorsqu'un missile sort de cette zone, il est supprimé pour laisser de la place au nouveaux crées. 
7. **Collisions** :
   - La collision entre le joueur et un missile ennemi provoque la perte d'une vie.
   Lorsque cela se produit il y a deux scénarios. Soit le joueur a encore des vies et peut continuer à jouer, donc lui et tous les monstres encore en vie réaparaîssent en formation initiale après un compte à rebours. Soit le joueur n'a plus de vie et donc termine la partie par un GAME OVER.
   - La collision entre le joueur et un autre monstre provoque la perte d'une vie également.
   Par contre, le monstre est aussi tué, donc si nous sommes dans le cas où le joueur a encore de la vie, alors la valeur du monstre mort nous est ajouté au score de la partie actuelle.
8. **Niveaux** : 2 niveaux avec configurations différentes, chargées depuis les fichiers correspondants.
9. **Meilleur score** : Sauvegardé en fichier et chargé au démarrage. S'affiche en haut de l'écran et également à la fin d'une partie dans laquelle nous avons obtenu un nouveau record !
10.**Mode vies infinies** : s'active et se désactive à l'aide de la touche I sur le clavier.


Classes principales

### 1. **engine/App.java**
**Point d'entrée** du programme.
Crée une instance de `Game` et lance la boucle de jeu.

### 2. **engine/StdDraw.java**
**Moteur graphique** personnalisé utilisant Java Swing.
Nous n'y avons pas vraiment touché.

### 3. **Game.java**
**Classe principale du jeu** 
Gère la boucle de jeu, les états, les collisions, le score.
Nous avons suivi l'idée qui était déjà là de base, en partageant le travail entre une méthode update pour les mouvements et changements en temps réel, et une méthode draw pour l'affichage du jeu.

**Attributs clés :**
- `etat_jeu` : Nous avons séparé ici les différents états du jeu comme pour une machine à état.
Cela a simplifié notamment les passages entre l'écran d'accueil, la partie ou les différentes fins et même la séléction du niveau qui au départ avait été faite pour vérifier le fonctionnement correct du niveau 2 sans passer par le niveau 1 (par manque d'impatience).
            |        État      | Valeur |              Description              |
            |------------------|--------|---------------------------------------|
            | Sélection niveau |   -1   |    Attends le joueur (touches 1/2)    |
            |   Début partie   |    0   |     Affiche "Appuyez sur espace"      |
            | Affichage niveau |    1   | Montre le numéro du niveau pendant 2s |
            |       Jeu        |    2   |          Boucle de jeu active         |
            |      Victoire    |    3   |       Tous les niveaux complétés      |
            |      Pause       |    4   |    Compte à rebours entre les vies    |
            |    Game Over     |    5   |         Toutes les vies perdues       |
- `viesInfinies` : boolean permettant de savoir si nous sommes dans le mode vies infinies ou non, ce boolean change de valeur à chaque fois que nous appuions sur la touche i. 
- `listFormations` : liste des formations de monstres par niveau. Chaque niveau a sa propre formation.
- `player` : l'entité du joueur.
- `score` / `bestScore` : scores actuels et meilleur.
- `niveau_actuel` : niveau en cours (dans notre cas 1 ou 2).

**Méthodes clés :**
- `launch()` : initialise et lance la boucle de jeu
- `update()` : met à jour tous les éléments (entrées, positions, collisions)
- `draw()` : affiche tous les éléments selon l'état
- `playerTouche()` : vérifie les collisions du joueur avec missiles et monstres
- `suppressionMissilesPlayer()` : supprime les missiles hors-écran ou en collision
- `monstreTouche(Monster m)` : applique dégâts et rend la vie si Moth captureur détruit
- `replay(int level)` : réinitialise le jeu pour un niveau donné
- `chargementBestScore()` / `saveNewBestScore()` : gère la persistence du meilleur score


### 4. **Niveaux.java**
**Charge et interprète les fichiers de niveaux** (.lvl).

**Attributs :**
- `monstres` : liste des monstres du niveau
- `vitesse` : vitesse de la formation
- `cooldownAttaques` : délai entre les attaques (en ms)
- `cooldownTirs` : délai entre les tirs (en ms)

**Méthodes :**
- `getMonstres()`, `getVitesse()`, `getCooldownAttaques()`, `getCooldownTirs()` : getters

**Format des fichiers .lvl :**
```
Level1 0.001 -1 4000
Bee 0.5 0.9 0.03 10 2
Bee 0.55 0.9 0.03 10 2
...
```
Première ligne : nom, vitesse, cooldownAttaques (-1 = pas d'attaque), cooldownTirs  
Lignes suivantes : type, posX, posY, taille, valeur, cooldownTir

### 5. **Mouvements.java**
**Classe abstraite** définissant les mouvements de base.

**Attributs :**
- `posX`, `posY` : position
- `vitesse` : vitesse de déplacement
- `length` : taille de l'entité

**Méthodes :**
- `mouvementDroit()` : déplace à droite, stoppe aux murs
- `mouvementGauche()` : déplace à gauche, stoppe aux murs
- `mouvementHaut()` : déplace vers le haut
- `mouvementBas()` : déplace vers le bas
- `getPosX()`, `getPosY()`, `getLength()`, `getVitesse()` : getters

### 6. **Entite.java**
**Classe parente** pour Player et Monster — gère la vie, les dégâts, l'affichage via sprite ASCII.

**Attributs :**
- `hp` : points de vie
- `sprite` : chaîne ASCII représentant l'entité
- `tabPixels` : tableau 2D de caractères (pixels)
- `Xinit`, `Yinit` : position initiale

**Méthodes :**
- `degats(int degats)` : réduit les HP
- `isDead()` : retourne vrai si HP == 0
- `draw()` : affiche le sprite pixel par pixel
- `loadSprite(String path)` : charge un fichier .spr

### 7. **Player.java**
**Le vaisseau du joueur**.

**Attributs :**
- `countdownSpacePressed` : délai entre les tirs

**Méthodes :**
- `update(List<Missiles> missilesDispo)` : met à jour position (flèches) et tir (espace)
- `creeMissile(List<Missiles> missilesDispo)` : crée un missile si < 3 actifs
- `perdreVie(boolean modeInfini)` : réduit HP sauf si `modeInfini` est vrai
- `gagnerVie()` : récupère une vie (utilisé quand Moth captureur est tué)

---

### 8. **Monster.java**
**Classe parente** pour tous les monstres.

**Attributs :**
- `valeur` : points gagnés en détruisant le monstre
- `enAttaque` : indique si le monstre attaque (sort de la formation)
- `cooldownTir` : délai avant le prochain tir

**Méthodes :**
- `update(boolean directionDroite, Player p, boolean modeInfini)` : à implémenter dans les sous-classes
- `creeMissile(List<Missiles> missilesMonstres)` : crée un missile ennemi si cooldown écoulé
- `isOneOfFirst(List<Monster> monstres)` : retourne vrai si le monstre n'est pas bloqué par d'autres (peut attaquer)
- `isGone()` : retourne vrai si le monstre a dépassé le bas de l'écran

### 9. **Formation.java**
**Gère la formation de monstres** : mouvement collectif, sélection des attaquants, tirs.

**Attributs :**
- `listeMonstres` : monstres en formation
- `listeMonstresHorsFormation` : monstres en attaque
- `listeMissilesEnnemis` : missiles ennemis
- `directionDroite` : direction actuelle (true = droite, false = gauche)
- `cooldownAttaques` : délai avant la prochaine vague d'attaques

**Méthodes :**
- `update(Player p, boolean modeInfini)` :
  - Supprime les monstres morts ou sortis
  - Change direction si collision avec un mur
  - Met à jour tous les monstres
  - Sélectionne aléatoirement des monstres "en avant" pour attaquer
  - Gère les tirs
- `draw()` : affiche formation et missiles
- `recommencer()` : remet tous les monstres en formation
- `niveauTermine()` : retourne vrai si aucun monstre restant

### 10. **Missiles.java**
**Projectiles** (joueur ou monstres).

**Attributs :**
- `estDuPlayer` : vrai si c'est un missile du joueur
- `longueur`, `largeur` : dimensions

**Méthodes :**
- `update()` : met à jour la position (monte pour joueur, descend pour monstres)
- `draw()` : dessine le missile

### 11. **Butterfly.java / Bee.java**
**Monstre papillon** — attaque en descendant droit.
**Monstre abeille** — attaque en descendant avec mouvement sinusoïdal.


**Méthode clé :**
- Butterfly -> `mouvement_attaque()` : descend lentement et droit
- Bee -> `mouvement_attaque()` : descend rapidement et oscille horizontalement



### 12. **Moth.java**
**Monstre papillon de nuit** — attaque spéciale : descend, capture le vaisseau (perte de vie), puis remonte.

**Attributs :**
- `capture` : true si le vaisseau a été capturé

**Méthodes :**
- `verifierCapture(Player p, boolean modeInfini)` : vérifie collision horizontale, applique dégât et snap
- `mouvement_attaque(Player p, boolean directionDroite, boolean modeInfini)` :
  - Si non capturé : descend et pourchasse le joueur horizontalement
  - Si capturé : remonte lentement et se déplace avec la formation

### 13. **Partie.java**
**Affichage des écrans** (menus, niveaux, etc.).

**Méthodes :**
- `debut_partie_draw()` : écran "appuyez sur espace pour commencer"
- `niveau_affichage_draw()` : affiche le numéro du niveau pendant 2 secondes
- `selection_niveau_draw()` : écran "appuyez sur 1 ou 2 pour choisir"
- `win_draw()` : écran de victoire
- `gameOver_draw()` : écran de fin


### 14. **ZoneScore.java**
**Affichage du score** en bas de l'écran.

**Méthodes :**
- `draw()` : affiche le score actuel et meilleur
- `update(int score, int bestScore)` : met à jour les valeurs


### 15. **ZoneInfo.java**
**Affichage des vies** (icônes du vaisseau) et du niveau.

**Méthodes :**
- `draw(int hp, boolean modeInfini)` : affiche les icônes des vaisseaux représentant les vies restantes  et "INFINITE LIVES" si le mode de vie infinie est actif. Il n'affiche rien s'il est desactivé.
Il dessine également le symbole niveaux.

### 16. **ZoneCompteRebours.java**
**Affichage du compte à rebours** entre les vies perdues. Cela permet d'avoir un court temps de pause entre chaque mort du joueur et nous préparer à revenir dans le jeu. 

**Méthodes :**
- `draw()` : affiche le compte à rebours
- `update(int compteRebours)` : met à jour la valeur