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
    var selectedKlant = null;
    var selectedBestelling = null;
    var selectedFactuur = null;
    var betalingURL = "http://localhost:40847/RestTest/rest/betaling";
    //var betaalwijzeURL = "http://localhost:40847/RestTest/rest/betaalwijze/";
    var factuurURL = "http://localhost:40847/RestTest/rest/factuur/";
    var bhaURL = "http://localhost:40847/RestTest/rest/bestellinghasartikel/" + bestellingId;
    alert(bestellingId);   
    
    //getBestelling
     $.getJSON(bestellingURL, function(result){
        selectedBestelling = result;
    });
    
    var klantId = selectedBestelling.Klant_idklant;
    var klantURL = "http://localhost:40847/RestTest/rest/klant/" + klantId;
    var bestellingURL = "http://localhost:40847/RestTest/rest/klant/" + klantId +"/bestelling/"; + bestellingId;
     //getKlant
    $.getJSON(klantURL, function(result){
        selectedKlant = result;
    });
    
    alert(klantId);
    
    getArtikelenInBestelling();
    
    $.ajax({
           type: 'GET' ,
           url: bhaURL,
           dataType:"json" ,
           success: function(data){
               //alert("success");
               displayBestelling(data);
           },
           failure: function(data){
               //alert("fout");
               displayBestelling(data);
           }
        });
    
    function getArtikelenInBestelling(){
        $.ajax({
           type: 'GET' ,
           url: bhaURL,
           dataType:"json" ,
           success: function(data){
               //alert("success");
               displayBestelling(data);
           },
           failure: function(data){
               //alert("fout");
               displayBestelling(data);
           }
        });
    }
    //displayBestelling
    function displayBestelling(result) {
        //bha = result;
        $('#bestellingBody').empty(); 
        $.each(result, function(i, bestellingHasArtikel) {     
            $('#bestellingBody').append("<tr id='" + bestellingHasArtikel.idBestelArtikel + "'></tr>");
            
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelnaam'>" + bestellingHasArtikel.artikelidArtikel.artikelnaam + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelnummer'>" + bestellingHasArtikel.artikelidArtikel.artikelnummer + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelomschrijving'>" + bestellingHasArtikel.artikelidArtikel.artikelomschrijving + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelprijs'>" + bestellingHasArtikel.artikelidArtikel.artikelprijs + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='aantal'>" + bestellingHasArtikel.aantal + "</td>");
        });
    }
    //get betaalwijzes
    $.getJSON(betalingURL + "/betaalwijze", function(result){
        $.each(result, function(i, betaalwijze){
            $("#selectBetaalMethode").append("<option value='" + betaalwijze.idBetaalwijze + "'>" + betaalwijze.betaalwijze + "</option>");
            if (i===0){
                $("option:first").attr('selected', 'selected');
            } 
        });
    });
    
    
    $(document).on('click', "button#betaalDeBestelling", function(event){
        event.preventDefault();
        var betaalwijzeId = $("select#selectBetaalMethode").find(":selected").val();
        var betaalwijze = $("select#selectBetaalMethode").find(":selected").text();
        var betalinggegevens = $("input#overigeGegevens").val();
       
        //maak factuur en zet deze in database
        var jsonFactuur = JSON.stringify({
            "bestelling_idBestelling": {"idBestelling": selectedBestelling.idBestelling, "Klant_idklant": selectedBestelling.Klant_idklant}
        });
        
        ajaxCreateFactuur(factuurURL, jsonFactuur);
        
        //get factuur
         $.getJSON(factuurURL + factuurId, function(result){
            selectedFactuur = result;
        });
        
        
        //maak betaling en zet deze in database
        var jsonBetaling = JSON.stringify({
            "klant_idKlant": {"idKlant": selectedKlant.idKlant, "voornaam": selectedKlant.voornaam, "tussenvoegsel": selectedKlant.tussenvoegsel,
                "achternaam": selectedKlant.achternaam, "email": selectedKlant.email},
            "factuur_idFactuur": {"idFactuur": selectedFactuur.idFactuur, "factuur_datum": selectedFactuur.factuurDatum, 
                "bestellingId_Bestelling": selectedFactuur.bestellingIdBestelling },
            "betaalwijze_idBetaalwijze": {"idBetaalwijze": betaalwijzeId, "betaalwijze": betaalwijze},
            "betalinggegevens": {betalinggegevens}   
        });
        
        ajaxCreateBetaling(betalingURL ,jsonBetaling);
        
        //terug naar klantoverzicht
        var id = {klantId : selectedKlant.idKlant};
        var idParam = $.param(id);
        window.location.href = "viewKlant.html?" + idParam;
        
    })
    
    function ajaxCreateFactuur(URL, object) {
        $.ajax({
            type: 'POST',
            contentType: 'json',
            url: URL,
            dataType: "json",
            data: object,
            success: function(data, textStatus, jqXHR){
                alert("factuur gemaakt");
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + object);
                alert("fout bij factuur maken");
            }
        });
    };
           
    function ajaxCreateBetaling(URL, object) {
        $.ajax({
            type: 'POST',
            contentType: 'json',
            url: URL,
            dataType: "json",
            data: object,
            success: function(data, textStatus, jqXHR){
                alert("factuur gedaan");
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + object);
                alert("fout bij betaling");
            }
        });
    };
});
