/* example trouvé
public void plot2d() 
{
    JGnuplot jg = new JGnuplot();
    Plot plot = new Plot("") {
        {
            xlabel = "x";
            ylabel = "y";
        }
    };
    double[] x = { 1, 2, 3, 4, 5 }, y1 = { 2, 4, 6, 8, 10 }, y2 = { 3, 6, 9, 12, 15 };
    DataTableSet dts = plot.addNewDataTableSet("2D Plot");
    dts.addNewDataTable("y=2x", x, y1);
    dts.addNewDataTable("y=3x", x, y2);
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
            xlabel = "x";	// abssisse = nb de tours
            ylabel = "y";	// ordonné = nb de ressource obtenu	
        }
    };

    int nb_Joueurs = getNbJoueurs
    int nb_Tours = getNbTour(); 		// admeton une fonction qui cherche donne le nombre de tours
    double[] x ;
    for (int i = 0; i<=nb_Tours; i++)
    {	x [i] = i;	
    }



    DataTableSet dts = plot.addNewDataTableSet("2D Plot");		
    double [nb_Tours]	;	// on va le remplir puis le add recursivement pour tout les joueurs

    for (int j = 0; j < nb_Joueurs; )  	  	// pour chaque joueur
    {
	ressousesAquisses = getRessoursesJoueur( j );	
	// admeton une fonction qui retourne les valeurs des ressourses dans l'ordre du joueur j	
	// doit commencer par 0 car au tour 0 ils on 0 ressourses (a initialiser?)
	
    	dts.addNewDataTable("y=0", x, ressousesAquisses);		
	// Je suis pas sur de pouvoir tracer la courbe (d'ou le y=0 pour la cacher) vue c'est pas une
	// droite.. sauf il on fait une aprox a la G3D + IA? XD
    }


    jg.execute(plot, jg.plot2d);
}
*/
