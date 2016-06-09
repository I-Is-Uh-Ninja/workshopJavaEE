/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
    //method to get the parameter in the url
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
    
    var bestellingId = getUrlParameter('bestellingId');
    var URL = "http://localhost:40847/RestTest/rest/bestellinghasartikel" + bestellingId;
    //var bha = null;
    getArtikelenInBestelling();
    
    function getArtikelenInBestelling(){
        $.ajax({
           type: 'GET' ,
           url: URL,
           dataType:"json" ,
           success: function(data){
               displayTableBHA(data);
           },
           failure: function(data){
               displayTableBHA(data);
           }
        });
    }
   
    function displayTableBHA(result) {
        //bha = result;
        $('#artikelenInBestelling').empty();
        $.each(result, function(i, bestellingHasArtikel) {
            $('#artikelenInBestelling').append("<tr id='" + bestellingHasArtikel.idBestelArtikel + "'></tr>");
            
            $('#artikelenInBestelling tr#' + bestellingHasArtikel.idBestelArtikel).append("<tr id='" + bestellingHasArtikel.artikelIdArtikel.artikelnaam + "'></tr>");
            $('#artikelenInBestelling tr#' + bestellingHasArtikel.idBestelArtikel).append("<tr id='" + bestellingHasArtikel.artikelIdArtikel.artikelnummer + "'></tr>");
            $('#artikelenInBestelling tr#' + bestellingHasArtikel.idBestelArtikel).append("<tr id='" + bestellingHasArtikel.artikelIdArtikel.artikelomschrijving + "'></tr>");
            $('#artikelenInBestelling tr#' + bestellingHasArtikel.idBestelArtikel).append("<tr id='" + bestellingHasArtikel.artikelIdArtikel.artikelprijs + "'></tr>");
            
            $('#artikelenInBestelling tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='editAantal'><button type='button' id='" + bestellingHasArtikel.idBestelArtikel + "'>Pas aantal aan</button></td>");
            $('#artikelenInBestelling tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='delArtikel'><button type='button' id='" + bestellingHasArtikel.idBestelArtikel + "'>Verwijder artikel</button></td>");
        });
    }
    
    
    
    
    //door na betaalBestelling, werkt niet
    $("button#naarBetaling").on("click", function() {
        event.preventDefault();
        var id = {bestellingId : bestellingId};
        var idParam = $.param(id);
        location.href = "betaling.html?" + idParam;
    });
    //door na selectArtikel, werkt niet
    $("button#addArtikel").on("click", function() {
        event.preventDefault();
        var id = {bestellingId : bestellingId};
        var idParam = $.param(id);
        location.href = "selectArtikel.html?" + idParam;
    });
});