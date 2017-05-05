
/* 
//Utilisation de gnuplot pour tracer les résultat du jeu.

public void plot2d() 
{
    JGnuplot jg = new JGnuplot();
    Plot plot = new Plot("") {
        {
            xlabel = "x";	// abscisse = nb de tours
            ylabel = "y";	// ordonnÃƒÂ© = taux de complétion	
        }
    };

    int nb_Joueurs = getNbJoueurs
    int nb_Tours = getNbTour(); 		// fonction qui cherche donne le nombre de tours
    double[] x ;
    for (int i = 0; i<=nb_Tours; i++)		
    {	x [i] = i;	
    }



    DataTableSet dts = plot.addNewDataTableSet("2D Plot");		
    double TauxCompletion [nb_Tours]	;	// on va le remplir puis le add recursivement pour tout les joueurs

    for (int j = 0; j < nb_Joueurs; )  	  	// pour chaque joueur
    {
	TauxCompletion = getRessoursesJoueur( TauxCompletion, j );	
	// fonction qui ajoute les taux de complétion dans l'ordre du joueur j dans le tableau ressoursesAquisses	
	// doit commencer par 0 car au tour 0 ils on 0 ressourses (a initialiser?)
	
    	dts.addNewDataTable("y=0", x, TauxCompletion);		
	// Je suis pas sur de pouvoir tracer la courbe (d'ou le y=0 pour la cacher) vue c'est pas une droite
	
    }


    jg.execute(plot, jg.plot2d);
}
*/





