/* example trouvé
public void plot2d() 
{
    JGnuplot jg = new JGnuplot();		// Init
    Plot plot = new Plot("") {
        {
            xlabel = "x";			// Init abscisse
            ylabel = "y";			// Init ordonne
        }
    };
    double[] x = { 1, 2, 3, 4, 5 },		// point d'abssisse x selectioner
	     y1 = { 2, 4, 6, 8, 10 },		// valeur de y1(x)
	     y2 = { 3, 6, 9, 12, 15 };		// valeur de y2(x)
    DataTableSet dts = plot.addNewDataTableSet("2D Plot");
    dts.addNewDataTable("y=2x", x, y1);		// plot des points de coordonnées (x[i],y1[i]) ET de la courbe y=2x
    dts.addNewDataTable("y=3x", x, y2); 	// plot des points de coordonnées (x[i],y2[i]) ET de la courbe y=3x
    jg.execute(plot, jg.plot2d);
}
*/

/*
// Aplication
public void plot2d() 
{
    JGnuplot jg = new JGnuplot();
    Plot plot = new Plot("") {
        {
            xlabel = "x";	// abscisse = nb de tours
            ylabel = "y";	// ordonnÃ© = nb de ressource obtenu	
        }
    };

    int nb_Joueurs = getNbJoueurs
    int nb_Tours = getNbTour(); 		// admeton une fonction qui cherche donne le nombre de tours
    double[] x ;
    for (int i = 0; i<=nb_Tours; i++)		
    {	x [i] = i;	
    }



    DataTableSet dts = plot.addNewDataTableSet("2D Plot");		
    double ressoursesAquisses [nb_Tours]	;	// on va le remplir puis le add recursivement pour tout les joueurs

    for (int j = 0; j < nb_Joueurs; )  	  	// pour chaque joueur
    {
	ressoursesAquisses = getRessoursesJoueur( ressoursesAquisses, j );	
	// admeton une fonction qui retourne les valeurs des ressourses dans l'ordre du joueur j dans le tableau ressoursesAquisses	
	// doit commencer par 0 car au tour 0 ils on 0 ressourses (a initialiser?)
	
    	dts.addNewDataTable("y=0", x, ressoursesAquisses);		
	// Je suis pas sur de pouvoir tracer la courbe (d'ou le y=0 pour la cacher) vue c'est pas une
	// droite.. sauf il on fait une aprox a la G3D + IA? XD
    }


    jg.execute(plot, jg.plot2d);
}
*/



