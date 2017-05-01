########################################################################
# Makefile
# Projet : Jeux et Stratégies
# Auteur : Romain PERRIN 
########################################################################

# compile les fichiers du projet
compil:
	echo "Compilation des fichiers du projet pour test"
	javac *.java
	
# nettoie le répertoire du projet
clean:
	echo "Nettoyage de tous les fichier class"
	rm *.class
	
########################################################################
# cibles temporaires (utilisées pour les tests)
########################################################################

# initialise un remiregistry sur le port 9000
reg:
	rmiregistry 9000 &

# lance un coordinateur avec 3 agents et 1 producteur
# format des arguments de CoordinateurImpl :
# <port rmiregistry> <nbAgents> <nbProducteurs> <FinPremierAgent> <FichierLog>
# arguments utilisés : 9000 3 1 gold 100
coord:
	java CoordinateurImpl 9000 2 1 false log

# lance un producteur
# format des arguments de ProducteurImpl :
# <port rmiregistry> <idProducteur> <typeRessource> <quantiteRessource> <quantiteMax>
# arguments utilisés : 9000 0 gold 200 10 silver 200 10
prod:
	java ProducteurImpl 9000 0 gold 200 10 silver 200 10

# lance un agent (trois versions un coop, un indiv et un humain)
# format des arguments de Agentxxx :
# <port du rmiregistry> <idAgent> <typeRessource> <quantiteRessource> <objectif> (triplet à répéter)
# arguments utilisés : 9000 0,1,...,n gold 0 50 silver 0 100
agent1:
	java AgentCoop 9000 0 gold 0 50 silver 0 100
	
agent2:
	java AgentHumain 9000 1 gold 0 50 silver 0 100
