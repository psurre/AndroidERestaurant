package fr.isen.surre.androiderestaurant

/**
 * Fichier des constantes du projet
 */

// Clés pour les différents Intent
const val BASKETKEY="basket"
const val KEYDISHES = "key.dishes"
const val BASKET = "basket.activity"
const val KEYDETAILTXT = "key.detail.txt"

// Valeurs limites
const val MAXQTY = 50 // Nombre d'articles maximum dans le panier
const val MINQTY = 1 // Nombre minimum à afficher dans la page de commande d'un plat
const val MINSIZE = 4 // Longueur minimale d'un champ
const val MINMDPSIZE = 8 // Longueur minimale pour le mot de passe

// URL des webservices
const val URLMENU="http://test.api.catering.bluecodegames.com/menu"
const val URLLOGIN="http://test.api.catering.bluecodegames.com/user/login"
const val URLORDER="http://test.api.catering.bluecodegames.com/user/order"
const val URLREGISTER="http://test.api.catering.bluecodegames.com/user/register"

// Messages
const val TRUERETURN = "NOERROR"