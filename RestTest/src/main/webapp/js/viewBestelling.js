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
    var URL = "http://localhost:40847/RestTest/rest/bestellinghasartikel/" + bestellingId;
    //var bha = null;
    //getArtikelenInBestelling();
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
    
    /*function getArtikelenInBestelling(){
        $.ajax({
           type: 'GET' ,
           url: URL,
           dataType:"json" ,
           success: function(data){
               alert("success");
               displayTableBHA(data);
           },
           failure: function(data){
               alert("fout");
               displayTableBHA(data);
           }
        });
    }*/
   
    function displayTableBHA(result) {
        //bha = result;
        $('#bestellingBody').empty(); 
        $.each(result, function(i, bestellingHasArtikel) {
            alert(bestellingHasArtikel.idBestelArtikel);
            
            $('#bestellingBody').append("<tr id='" + bestellingHasArtikel.idBestelArtikel + "'></tr>");
            
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelnaam'>" + bestellingHasArtikel.artikelidArtikel.artikelnaam + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelnummer'>" + bestellingHasArtikel.artikelidArtikel.artikelnummer + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelomschrijving'>" + bestellingHasArtikel.artikelidArtikel.artikelomschrijving + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelprijs'>" + bestellingHasArtikel.artikelidArtikel.artikelprijs + "</td>");
            
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='editAantal'><button type='button' id='" + bestellingHasArtikel.idBestelArtikel + "'>Pas aantal aan</button></td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='delArtikel'><button type='button' id='" + bestellingHasArtikel.idBestelArtikel + "'>Verwijder artikel</button></td>");
        });
    }
    
    
    
    
    //door na betaalBestelling
    $("button#naarBetaling").on("click", function() {
        event.preventDefault();
        var id = {bestellingId : bestellingId};
        var idParam = $.param(id);
        location.href = "betaling.html?" + idParam;
    });
    //door na selectArtikel
    $("button#addArtikel").on("click", function() {
        event.preventDefault();
        var id = {bestellingId : bestellingId};
        var idParam = $.param(id);
        location.href = "selectArtikel.html?" + idParam;
    });
});