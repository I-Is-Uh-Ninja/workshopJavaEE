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
    
    //initialize variables
    var klantId = getUrlParameter('klantId');
    var restURL = "http://localhost:8080/RestTest/rest/klant/" + klantId;
    var klant = null;
    
    //getting info for tables
    //klant
    getKlant();
    
    function getKlant(){
        $.getJSON(restURL, function(result){
            klant = result;
            $("#klantBody").empty();
            $("#klantBody").append("<tr id='" + result.idKlant + "'></tr>");
            $("#klantBody tr").append("<td>" + result.voornaam + "</td>");
            $("#klantBody tr").append("<td>" + result.tussenvoegsel + "</td>");
            $("#klantBody tr").append("<td>" + result.achternaam + "</td>");
            $("#klantBody tr").append("<td>" + result.email + "</td>");
        });
    }
    
    //adres
    var adresURL = restURL + "/adres";
    $.getJSON(adresURL, function(result){
        $.each(result, function(i, klantHasAdres){
            $("#adresBody").append("<tr id='" + klantHasAdres.idKlanthasadres + "'></tr>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adresidAdres.straatnaam + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adresidAdres.huisnummer + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adresidAdres.postcode + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adresidAdres.woonplaats + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adrestypeidAdrestype.adresType + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td id='editAdres'><button type='button' id='"+ klantHasAdres.idKlanthasadres + "'>Bekijken</button></td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td id='deleteAdres'><button type='button' id='"+ klantHasAdres.idKlanthasadres + "'>Verwijderen</button></td>");
        });
    });
    
    //account
    var accountURL = restURL + "/account";
    $.getJSON(accountURL, function(result){
        $.each(result, function(i, account){
            $("#accountBody").append("<tr id='" + account.idAccount + "'></tr>");
            $("#accountBody tr#" + account.idAccount).append("<td>" + account.accountNaam + "</td>");
            $("#accountBody tr#" + account.idAccount).append("<td>" + account.creatieDatum + "</td>");
            $("#accountBody tr#" + account.idAccount).append("<td id='editAccount'><button type='button' id='"+ account.idAccount + "'>Bekijken</button></td>");
            $("#accountBody tr#" + account.idAccount).append("<td id='deleteAccount'><button type='button' id='"+ account.idAccount + "'>Verwijderen</button></td>");
        });
    });
    
    //bestelling
    var bestellingURL = restURL + "/bestelling";
    $.getJSON(bestellingURL, function(result){
        $.each(result, function(i, bestelling){
            $("#bestellingBody").append("<tr id='" + bestelling.idBestelling + "'></tr>");
            $("#bestellingBody tr#" + bestelling.idBestelling).append("<td>" + bestelling.idBestelling + "</td>");
            $("#bestellingBody tr#" + bestelling.idBestelling).append("<td id='viewBestelling'><button id='" + bestelling.idBestelling + "'>Bekijk bestelling</button></td>");
            $("#bestellingBody tr#" + bestelling.idBestelling).append("<td id='deleteBestelling'><button id='" + bestelling.idBestelling + "'>Verwijder bestelling</button></td>");
        });
    });
    
    //functions for buttons
    
    //edit klant
    var editKlantClicked = false;
    $("button#editKlant").click(function(){
        if(editKlantClicked === false){
            $("#klantBody tr").empty();
            $("#klantBody tr").append("<td><input type='text' id='voornaam' size='20' value='" + klant.voornaam + "' /></td>");
            $("#klantBody tr").append("<td><input type='text' id='tussenvoegsel' size='10' value='" + klant.tussenvoegsel + "' /></td>");
            $("#klantBody tr").append("<td><input type='text' id='achternaam' size='20' value='" + klant.achternaam + "' /></td>");
            $("#klantBody tr").append("<td><input type='text' id='email' size='40' value='" + klant.email + "' /></td>");
            editKlantClicked = true;
        }
        else{
            $.ajax({
                type: 'PUT',
                url: restURL,
                success: function(data, textStatus, jqXHR){
                    //alert("Success!");
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("Error: " + textStatus + "\n" + errorThrown);
                }
            });
            getKlant();
            editKlantClicked = false;
        }
    });
    
    //delete adres
    $(document).on("click", "td#deleteAdres button", function(){
        var URL = adresURL + "/" + event.target.id;
        ajaxDelete(URL);
    });
    
    //delete account
    $(document).on("click", "td#deleteAccount button", function(){
        var URL = accountURL + "/" + event.target.id;
        ajaxDelete(URL);
    });
    
    //delete bestelling
    $(document).on("click", "td#deleteAccount button", function(){
        var URL = bestellingURL + "/" + event.target.id;
        ajaxDelete(URL);
    });
    
});

