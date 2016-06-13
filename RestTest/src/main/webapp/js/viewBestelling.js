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
    var bhaURL = "http://localhost:8080/RestTest/rest/bestellinghasartikel/";
    var URL = bhaURL + bestellingId;
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
    
    function getArtikelenInBestelling(){
        $.ajax({
           type: 'GET' ,
           url: URL,
           dataType:"json" ,
           success: function(data){
               //alert("success");
               displayTableBHA(data);
           },
           failure: function(data){
               //alert("fout");
               displayTableBHA(data);
           }
        });
    }
   
    function displayTableBHA(result) {
        //bha = result;
        $('#bestellingBody').empty(); 
        $.each(result, function(i, bestellingHasArtikel) {     
            $('#bestellingBody').append("<tr id='" + bestellingHasArtikel.idBestelArtikel + "'></tr>");
            
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelnaam'>" + bestellingHasArtikel.artikelidArtikel.artikelnaam + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelnummer'>" + bestellingHasArtikel.artikelidArtikel.artikelnummer + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelomschrijving'>" + bestellingHasArtikel.artikelidArtikel.artikelomschrijving + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelprijs'>" + bestellingHasArtikel.artikelidArtikel.artikelprijs + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='aantal'>" + bestellingHasArtikel.aantal + "</td>");
            
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='editAantal'><button type='button' id='" + bestellingHasArtikel.artikelidArtikel.idArtikel + "'>Pas aantal aan</button></td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='delArtikel'><button type='button' id='" + bestellingHasArtikel.idBestelArtikel + "'>Verwijder artikel</button></td>");
        });
    }
    
   
    
    
    //door na betaalBestelling
    $("button#naarBetaling").on("click", function(event) {
        event.preventDefault();
        var id = {bestellingId : bestellingId};
        var idParam = $.param(id);
        location.href = "betaling.html?" + idParam;
    });
    //door na selectArtikel
    $("button#addArtikel").on("click", function(event) {
        event.preventDefault();
        var id = {bestellingId : bestellingId};
        var idParam = $.param(id);
        location.href = "selectArtikel.html?" + idParam;
    });
    
    //delete artikel uit bestelling, onbekend of deze werkt?
    $(document).on("click", "td#delArtikel button", function(event){
       $.ajax({
           type: 'DELETE',
           url: bhaURL + event.target.id,
           succces: function (data, textStatus, jqHXR){
               displayTableBHA(data);
           },
           error: function(jqXHR, textStatus, errorThrown, data){
               displayTableBHA(data);    
           }
       });   
    });
    
    var selectedArtikel = null;
    var selectedBestelling = null;
    var bestURL = "http://localhost:8080/RestTest/rest/klant/" + klantId + "/bestelling/" + bestellingId;
    var artURL = "http://localhost:8080/RestTest/rest/artikel/";
    var klantId = getUrlParameter('klantId');
    
    $.getJSON(bestURL, function(result){
               selectedBestelling = result;
    });
    //$getJSON(artURL, function)
    
    var editAantalClicked = false;
    $(document).on("click", "td#editAantal button", function(event){
        event.preventDefault();
        var bhaId = $(this).parent().parent().attr("id");
        if(editAantalClicked === false) {
            //alert(bhaId);
            var existingAantal = $("tr#" + bhaId + " td.aantal").text();
            alert(existingAantal);
            $("tr#" + bhaId + " td.aantal").empty();
            $("tr#" + bhaId + " td.aantal").append("<input type='text' size=4 id='wijzigAantal' value= '" + existingAantal + "' /></td>");
            getSelectedArtikel(event.target.id);
            editAantalClicked = true;
        }
        else {
            var bhaJson = JSON.stringify({
                "idBestelArtikel": bhaId,
                "artikelidArtikel": selectedArtikel,
                "bestellingidBestelling": selectedBestelling,
                "aantal": $("input#wijzigAantal").val()
            });
            $.ajax({
                type: 'PUT',
                contentType: 'application/json',
                url: bhaURL + bhaId,
                data: bhaJson,
                dataType: 'json',
                success: function(data, textStatus, jqXHR){
                    //alert("Aantal gewijzigd: ");
                    editAantalClicked = false;
                    getArtikelenInBestelling();
               },
               error: function(jqXHR, textStatus, errorThrown){
                   alert("Error: " + textStatus + "\n" + errorThrown + "\n" + bhaJson);
               } 
            });
        } 
    });
    
    function getSelectedArtikel(id) {
        $.getJSON(artURL + id, function(result){
            selectedArtikel = result;
        });
    }
});