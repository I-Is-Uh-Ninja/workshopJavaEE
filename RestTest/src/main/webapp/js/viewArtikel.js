$(document).ready(function(){
    
     //method to get the parameter in the url, methode van Ian/internet(?) 
    var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
            sURLVariables = sPageURL.split('&'),
            sParameterName,
            i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    };
    
    //variabelen
    var artikelId = getURLParameter('artikelId');
    var URL = "http://localhost:40847/RestTest/rest/artikel" + artikelId;
    var artikel = null;  
    
    //get artikel info for table
    getArtikel();
    
    function getArtikel(){
        $getJSON(restURL, function(result)){
            artikel = result;
            $("#artikelBody").empty();
            $("#artikelBody").append("<tr id='" + result.idArtikel + "'></tr>");
            $("#artikelBody tr").append("<td>" + result.artikelnaam + "</td>");
            $("#artikelBody tr").append("<td>" + result.artikelnummer + "</td>");
            $("#artikelBody tr").append("<td>" + result.artikelomschrijving + "</td>");
            $("#artikelBody tr").append("<td>" + result.artikelprijs + "</td>");
        });
    }
    
    //edit methodes
    
}

