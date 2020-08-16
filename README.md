# INF600G - Conception de Logiciels Adaptés

- Auteurs: Velynn
    
- Le projet utilise l'emulateur Pixel C API 22, avec une résolution minimale de `2560` par `1800` pixels.

##  Description du produit réalisé


### Objectifs

  1. Comprendre les principes d'une démarche centrée utilisateurs;
  2. Caractériser des personas sur la base de la description d'une problématique;
  3. Caractériser et prioriser des récits utilisateurs pour spécifier un développement logiciel.
   
### Sujet d'étude : Un quiz pour les aînés en résidence.

Dans les résidences pour aînés, le personnel d'encadrement organise des activités à destination des résidents, pour animer la communauté. Ces activités servent à occuper les résidents qui pourraient trouver le temps long, mais ont aussi un rôle de suivi thérapeutique pour identifier de la manière la plus précoce possible des troubles liés à l'âge.

On s'interesse donc principalement  dans cette étude à deux classes d'utilisateurs : (i) les personnes _résidentes_ et (ii) les personnes _aidantes_ (animateurs ou animatrices, aide-soignant·e·s, ergothérapeutes, ...).

Pour permettre aux _aidant·e·s_ de se concentrer sur les résident·e·s ayant le plus de difficultés (_p.-ex._, infirmité motrice demandant un soutien au déplacement, démence sénile légère) et de leur apporter le meilleur service, il est souhaité fournir aux aînés un certain nombre d'activités sous forme numérique, en utilisant des tablettes numériques. Ceci permettra de remplir la fonction d'animation de la communauté, sans pour autant accaparer le personnel aidant.

La première de ces activités identifiée comme prioritaire pour une étude pilote est un système de _quiz_ à destination des personnes résidentes. On ajoutera par la suite d'autres jeux numériques si cette étude pilote est concluante. Parmi ce public, on distingue celles et ceux qui sont à l'aise avec l'outil numérique (ils ou elles utilisent régulièrement un téléphone intelligent par exemple), et les autres qui ne sont pas du tout à l'aise avec ce type de matériel.

Un quiz est une succession de questions à choix multiple. Les questions peuvent être purement textuelles, mais aussi accompagnées d'une image, d'une vidéo, d'un enregistrement audio. Une fois le quiz terminé, les résultats sont enregistrés et la personne résidente peut continuer à jouer, ou décider d'arrêter là.

Les quizs définis peuvent être de deux types : collectif ou individuel. Un quiz collectif est sur un thème d'interêt pour les résident·e·s, par exemple le sport, l'histoire, la culture générale, ... Ces quizs peuvent être proposés aux résident·e·s par les aidant·e·s, qui peuvent aussi en ajouter de nouveaux. Les quiz individuels sont définis spécifiquement pour un ou une résidente en particulier. Ces quizs sont définis en collaboration avec leurs familles, sur des événements d'importance pour eux. Ces quizes personalisés sont particulièrement interessant pour maintenir un lien mémoriel, et par exemple identifier des signes précoces de confusion parmi les personnes résidentes.

Du coté du personel aidant, en plus de la possibilité d'ajouter et de recommander de nouveaux quizes, la surveillance des résidents est très importante. Ils espèrent pouvoir utiliser les données collectées par l'application pour suivre la population résidente, par exemple en identifiant que le taux de bonnes réponses d'un certain résident est en déclin sur des thématiques où il était réputé être un connaisseur il y a peu.  Le nombre de quiz commencés mais non terminés est aussi un indicateur interessant pour le suivi des résidents. L'utilisation du support numérique peut aussi identifier des tremblements physiques anonciateur de troubles moteurs, des problématiques de vue (la personne ne clique plus vraiment là où il faut dans l'interface), ... Ces informations sont pertinentes pour le personel aidant, qui peut adapter une prise en charge thérapeutique au plus tôt (p.-ex., avec un ergothérapeute ou un physiothérapeute pour travailler un problème moteur en train de s'installer).

Le vieillissement entrainant des problèmes au long cours, on souhaite aussi pouvoir adapter l'application aux problèmes que pourra rencontrer une personne résidente au cours de son séjour dans la résidence. Par exemple une personne souffrant d'un trouble évolutif de la vision  (_p.-ex_, dégénérescence maculaire liée à l'âge, DMLA) pourra dans un premier temps utiliser une version adaptée de l'application (_p.-ex._ réponses plus grosses, non situées au centre pour une DMLA, ...), pour finalement basculer sur une version entièrement audio du système. Certaines adaptations interagissent avec la mécanique même du système de quiz : par exemple, pour des personnes atteintes de sénilité légère, les recommandations sont d'encourager la personne jusqu'à ce qu'elle trouve la bonne réponse plutôt que d'afficher une erreur (par exemple en restant sur la même question mais en enlevant la mauvaise réponse qui vient d'être selectionnée), et de se concentrer sur des quizs individuels pour stimuler sa mémoire en lien avec des évenements familiaux.

Une autre problématique liée à l'âge est l'apparition de troubles qui sont mineurs en isolation, mais qui s'accumulent et rendent difficile l'utilisation d'une l'application informatique : vue qui baisse, précision des gestes moindres, plus grand temps de changement de contexte, ... Parmi les adaptations disponibles pour les personnes résidentes, il est important de se poser la question de leur composition.


Le code source de l'application mobile (client) est dans le repertoire `Quiz`, et le code source de la partie arrière (serveur) est dans le repertoire `serveur`.

## Exécution du projet

Pour exécuter le projet, vous devez d'abord compiler et lancer la partie arrière, à l'aide de Maven :

```
mosser@lucifer serveur % mvn clean package jetty:run-war
```

Pour lancer l'application mobile dans un émulateur Android, Il suffit d'importer le projet dans Android Studio, puis de le lancer en cliquant sur la flèche verte.

### Utilisation de la partie arrière

Les ressource de partie arrière sont exposées à l'aide de Swagger, et disponible à l'adresse suivante :

- [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

Les ressources statiques utilisées par l'application sont stockées dans le repertoire `static`, et disponible à l'addresse suivante :

- [http://localhost:8080/static](http://localhost:8080/static)
