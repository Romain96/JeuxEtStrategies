# JEUX ET STRATEGIES 

## Description

Des agents se voient fixer un objectif de ressource(s) à atteindre.
Un ou plusieurs producteurs produit périodiquement un ou plusieurs types de ressources.
Les agents jouent au tour par tour et sont coordonnés par un coordinateur.
Durant son tour, un agent peut effectuer l'une des actions suivantes :
* tenter d'acquérir des ressources (le nombre demandé variant avec la peronnalité de l'agent)
* tenter de voler des ressources à un autre agent
* se metter dans un état de surveillance pendant le tour de jeu
* passer son tour (lorque celui-ci a été puni par le coordinateur en cas de vol répertorié)

Il est possible pour un humain de contrôler un joueur.
Toutes les transactions et actions seront sauvegardées et une interface
présentera l'historique global à la fin et éventuellement des statistiques.

## Outils

Le projet sera réalisé en Java et en utilisant l'API Remote Method Invocation (RMI)

## Fichiers

| Fichier | Description |
| ------- | ----------- |
| Coordinateur.java | interface RMI pour le coordinateur |
| Agent.java | interface RMI pour les agents |
| Producteur.java | interface RMI pour les producteurs |
| CoordinateurImpl.java | implémentation du coordinateur |
| AgentImpl.java | implémentation abstraite des agents |
| AgentCoop.java | implémentation concrète des agents avec une personnalité coopérative |
| AgentIndiv.java | implémentation concrète des agents avec une personnalité individuelle |
| Producteur.java | implémentation des producteurs |
| ObjetCoordinateur.java | objet utilisé comme coordinateur |
| ObjetAgent.java | objet utilisé comme agent |
| ObjetProducteur.java | objet utilisé comme producteur |
| Ressources.java | définition des ressources utilisées |
| objectifs.txt | définition des objectifs courants du projet |
| README.md | fichier descriptif |

## Auteurs

* BALZAN Juan
* PERRIN Romain


