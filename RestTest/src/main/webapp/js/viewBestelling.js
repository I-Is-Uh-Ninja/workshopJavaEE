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
    var factuurURL = "http://localhost:8080/RestTest/rest/factuur/findby/" + bestellingId;
    var totaalprijs = 0;
    //getArtikelenInBestelling();
    getFactuurGegevens();
    
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
    
    function getFactuurGegevens() {
        $.ajax({
          type: 'GET',  
          url: factuurURL,
          dataType: "json",
          success: function(data){
            //alert("Factuur gegevens opgehaald"); 
            displayFactuur(data);
          },
          failure: function(data){
             //alert("Factuur gegevens fout"); 
             displayFactuur(data);             
          }
        });
    }
    
    function getBetalingBijFactuur(URL) {
        $.ajax({
            type: 'GET',
            url: URL,
            dataType: "json",
            success: function(data){
                displayBetaling(data);
            },
            failure: function(data){
                displayBetaling(data);
            }
        });
    }
   
    function displayTableBHA(result) {
        $('#bestellingBody').empty();
        $('#totaalPrijs').empty();
        totaalprijs = 0;
        $.each(result, function(i, bestellingHasArtikel) {     
            $('#bestellingBody').append("<tr id='" + bestellingHasArtikel.idBestelArtikel + "'></tr>");
            
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='artikelnaam'>" + bestellingHasArtikel.artikelidArtikel.artikelnaam + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='artikelnummer'>" + bestellingHasArtikel.artikelidArtikel.artikelnummer + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='artikelomschrijving'>" + bestellingHasArtikel.artikelidArtikel.artikelomschrijving + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='artikelprijs'>" + bestellingHasArtikel.artikelidArtikel.artikelprijs + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='aantal'>" + bestellingHasArtikel.aantal + "</td>");
            
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='editAantal'><button type='button' id='" + bestellingHasArtikel.artikelidArtikel.idArtikel + "'>Pas aantal aan</button></td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='delArtikel'><button type='button' id='" + bestellingHasArtikel.idBestelArtikel + "'>Verwijder artikel</button></td>"); 
            
            var prijs = (bestellingHasArtikel.artikelidArtikel.artikelprijs * bestellingHasArtikel.aantal);   
            totaalprijs += prijs;         
        });
        $('#totaalPrijs').append(totaalprijs.toFixed(2));
    }
    
    
    //displayFactuur
    function displayFactuur(result) {
        $('#factuurBody').empty();
        $.each(result, function(i, factuur) {
           $('#factuurBody').append("<tr id='" + factuur.idFactuur + "'></tr>"); 
           
           $('#factuurBody tr#' + factuur.idFactuur).append("<td class='factuurid'>" + factuur.idFactuur + "</td>");
           $('#factuurBody tr#' + factuur.idFactuur).append("<td class='factuurdatum'>" + factuur.factuurDatum + "</td>");
           
          getBetalingBijFactuur("http://localhost:8080/RestTest/rest/betaling/findby/" + factuur.idFactuur);         
        });
    }
    
    function displayBetaling(result) {
        $.each(result, function(i, betaling) {
           $('#factuurBody tr#' + betaling.factuuridFactuur.idFactuur).append("<td class='betaalwijze'>" + betaling.betaalwijzeidBetaalwijze.betaalwijze + "</td>");
           $('#factuurBody tr#' + betaling.factuuridFactuur.idFactuur).append("<td class='betalingsgegevens'>" + betaling.betalingsGegevens + "</td>"); 
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
    $(document).on("click", "td.delArtikel button", function(event){
       event.preventDefault();
        $.ajax({
           type: 'DELETE',
           url: bhaURL + event.target.id,
           succces: function (data, textStatus, jqHXR){
               
           },
           error: function(jqXHR, textStatus, errorThrown, data){
           },
           complete: function(){
               getArtikelenInBestelling();
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
        
    var editAantalClicked = false;
    $(document).on("click", "td.editAantal button", function(event){
        event.preventDefault();
        var bhaId = $(this).parent().parent().attr("id");
        if(editAantalClicked === false) {
            //alert(bhaId);
            var existingAantal = $("tr#" + bhaId + " td.aantal").text();
            //alert(existingAantal);
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