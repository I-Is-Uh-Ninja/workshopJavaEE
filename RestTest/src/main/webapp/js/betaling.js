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
    var betalingURL = "http://localhost:8080/RestTest/rest/betaling";
    var betaalwijzeURL = "http://localhost:8080/RestTest/rest/betaalwijze";
    var factuurURL = "http://localhost:8080/RestTest/rest/factuur/";
    var bhaURL = "http://localhost:8080/RestTest/rest/bestellinghasartikel/" + bestellingId;
    var bestellingURL = "http://localhost:8080/RestTest/rest/bestellinghasartikel/bestelling/" + bestellingId;
    var totaalprijs = 0;
    
    //getBestelling
     $.getJSON(bestellingURL, function(result){
        selectedBestelling = result;
        selectedKlant = result.klantidKlant;
    });
    
    
    getArtikelenInBestelling();
   
    function getArtikelenInBestelling(){
        $.ajax({
           type: 'GET' ,
           url: bhaURL,
           dataType: "json",
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
        $('#bestellingBody').empty(); 
        $.each(result, function(i, bestellingHasArtikel) {
            $('#bestellingBody').append("<tr id='" + bestellingHasArtikel.idBestelArtikel + "'></tr>");
            
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelnaam'>" + bestellingHasArtikel.artikelidArtikel.artikelnaam + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelnummer'>" + bestellingHasArtikel.artikelidArtikel.artikelnummer + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelomschrijving'>" + bestellingHasArtikel.artikelidArtikel.artikelomschrijving + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td id='artikelprijs'>" + bestellingHasArtikel.artikelidArtikel.artikelprijs + "</td>");
            $('#bestellingBody tr#' + bestellingHasArtikel.idBestelArtikel).append("<td class='aantal'>" + bestellingHasArtikel.aantal + "</td>");
            
             var prijs = (bestellingHasArtikel.artikelidArtikel.artikelprijs * bestellingHasArtikel.aantal);   
            //alert(prijs);
            totaalprijs += prijs;         
        });
        $('#totaalPrijs').append(totaalprijs.toFixed(2));
    }
       
    //get betaalwijzes
    $.getJSON(betaalwijzeURL, function(result){
        $.each(result, function(i, betaalwijze){
            $("#selectBetaalMethode").append("<option value='" + betaalwijze.idBetaalwijze + "'>" + betaalwijze.betaalwijze + "</option>");
            if (i===0){
                $("option:first").attr('selected', 'selected');
            } 
        });
    });
    
    $(document).on('click', "button#betaalDeBestelling", function(event){
        event.preventDefault();
        
        //maak factuur en zet deze in database
        var jsonFactuur = JSON.stringify({
            "bestellingidBestelling": selectedBestelling,
            "factuurDatum": new Date()
        });
        
        ajaxCreateFactuur(factuurURL, jsonFactuur);
        
    });
    
    //back button clicked
    $("button#backToBestelling").click(function(){
        var id = {bestellingId : selectedBestelling.idBestelling};
        var idParam = $.param(id);
        window.location.href = "viewBestelling.html?" + idParam;
    });
    
    function createBetaling(){
        //maak betaling en zet deze in database
        var betaalwijzeId = $("select#selectBetaalMethode").find(":selected").val();
        var betaalwijze = $("select#selectBetaalMethode").find(":selected").text();
        var betalinggegevens = $("textarea#overigeGegevens").val();
        
        var jsonBetaling = JSON.stringify({
            "klantidKlant": selectedKlant,
            "factuuridFactuur": selectedFactuur,
            "betaalwijzeidBetaalwijze": {"idBetaalwijze": betaalwijzeId, "betaalwijze": betaalwijze},
            "betalingsGegevens": betalinggegevens,
            "betaalDatum": new Date()
        });

        ajaxCreateBetaling(betalingURL ,jsonBetaling);
        
    }
    
    function ajaxCreateFactuur(URL, object) {
        var factuurId = null;
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: URL,
            dataType: "json",
            data: object,
            success: function(data, textStatus, jqXHR){
                alert("factuur gemaakt\n" + data);
                factuurId = data;
                
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + object);
                alert("fout bij factuur maken \n" + object);
            },
            complete: function(){
                getFactuur(factuurId);
            }
        });
    };
           
    function ajaxCreateBetaling(URL, object) {
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: URL,
            dataType: "application/json",
            data: object,
            success: function(data, textStatus, jqXHR){
                alert("betaling gedaan");
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + object);
                alert("fout bij betaling \n" + object);
            },
            complete: function(){
                //terug naar bestllingoverzicht
                var id = {bestellingId : selectedBestelling.idBestelling};
                var idParam = $.param(id);
                window.location.href = "viewBestelling.html?" + idParam;
            }
        });
    };
    function getFactuur(id){
        $.ajax({
           type: 'GET' ,
           url: factuurURL + id,
           dataType:"json" ,
           success: function(data){
               //alert("success");
               selectedFactuur = data;
           },
           failure: function(data){
               //alert("fout");
               selectedFactuur = data;
           },
           complete: function(){
               createBetaling();
           }
        });
    }
});
