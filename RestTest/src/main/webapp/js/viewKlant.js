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
    var restURL = "http://localhost:40847/RestTest/rest/klant/" + klantId;
    var klant = null;
    
    //getting info for tables
    //klant
    getKlant();
    
    function getKlant(){
        $.ajax({
            type: 'GET',
            url: restURL,
            dataType: "json",
            success: function(data){
                displayTableKlant(data);
            }
        });
    }
    
    //display table for klant    
    function displayTableKlant(result){
        klant = result;
        $("#klantBody").empty();
        $("#klantBody").append("<tr id='" + result.idKlant + "'></tr>");
        $("#klantBody tr").append("<td>" + result.voornaam + "</td>");
        $("#klantBody tr").append("<td>" + result.tussenvoegsel + "</td>");
        $("#klantBody tr").append("<td>" + result.achternaam + "</td>");
        $("#klantBody tr").append("<td>" + result.email + "</td>");
    }
    
    //adres
    var adresURL = restURL + "/adres";
    getAdressen();
    function getAdressen(){
        $.ajax({
            type: 'GET',
            url: adresURL,
            dataType: "json",
            success: function(data){
                displayTableAdressen(data);
            },
            failure: function(data){
                displayTableAdressen(data);
            }
        });
    }
    
    function displayTableAdressen(result){
        $("#adresBody").empty();
        $.each(result, function(i, klantHasAdres){
            $("#adresBody").append("<tr id='" + klantHasAdres.idKlanthasadres + "'></tr>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adresidAdres.straatnaam + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adresidAdres.huisnummer + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adresidAdres.postcode + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adresidAdres.woonplaats + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td>" + klantHasAdres.adrestypeidAdrestype.adresType + "</td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td id='editAdres'><button type='button' id='"+ klantHasAdres.idKlanthasadres + "'>Bewerken</button></td>");
            $("#adresBody tr#" + klantHasAdres.idKlanthasadres).append("<td id='deleteAdres'><button type='button' id='"+ klantHasAdres.idKlanthasadres + "'>Verwijderen</button></td>");
        });
    }
    
    //account
    var accountURL = restURL + "/account";
    getAccounts();
    function getAccounts(){
        $.ajax({
            type: 'GET',
            url: accountURL,
            dataType: "json",
            success: function(data){
                displayTableAccounts(data);
            }
        });
    }
    
    function displayTableAccounts(result){
        $("#accountBody").empty();
        $.each(result, function(i, account){
            $("#accountBody").append("<tr id='" + account.idAccount + "'></tr>");
            $("#accountBody tr#" + account.idAccount).append("<td id='accountNaam'>" + account.accountNaam + "</td>");
            $("#accountBody tr#" + account.idAccount).append("<td id='accountCreatieDatum'>" + account.creatieDatum + "</td>");
            $("#accountBody tr#" + account.idAccount).append("<td id='editAccount'><button type='button' id='"+ account.idAccount + "'>Bewerken</button></td>");
            $("#accountBody tr#" + account.idAccount).append("<td id='deleteAccount'><button type='button' id='"+ account.idAccount + "'>Verwijderen</button></td>");
        });
    }
    
    //bestelling
    var bestellingURL = "http://localhost:40847/RestTest/rest/bestelling/bestellingByKlant/" + klantId;
    getBestellingen();
    
    function getBestellingen(){
        $.ajax({
            type: 'GET',
            url: bestellingURL,
            dataType: "json",
            success: function(data){
                displayBestellingenTable(data);
            },
            failure: function(data) {
                displayTableBestelling(data);
            }
        });
    }   
    
    function displayBestellingenTable(result){
        $("#bestellingBody").empty();
        $.each(result, function(i, bestelling){
            $("#bestellingBody").append("<tr id='" + bestelling.idBestelling + "'></tr>");
            $("#bestellingBody tr#" + bestelling.idBestelling).append("<td>" + bestelling.idBestelling + "</td>");
            $("#bestellingBody tr#" + bestelling.idBestelling).append("<td id='viewBestelling'><button id='" + bestelling.idBestelling + "'>Bekijk bestelling</button></td>");
            $("#bestellingBody tr#" + bestelling.idBestelling).append("<td id='deleteBestelling'><button id='" + bestelling.idBestelling + "'>Verwijder bestelling</button></td>");
        });
    }
    
    //functions for buttons
    
    //add adres
    $("button#addAdres").click(function(){
        event.preventDefault();
        var id = {klantId : klantId};
        var idParam = $.param(id);
        location.href = "addAdres.html?" + idParam;
    });
    
    //add account
    $("button#addAccount").click(function(){
        var accountUrl = restURL + "/account";
        var date = new Date();
        var jsonAccount = JSON.stringify({
            "accountNaam": $("#newAccountnaam").val(),
            "creatieDatum": date,
            "klantidKlant": klantId
        });
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: accountUrl,
            dataType: "application/json",
            data: jsonAccount,
            success: function(data, textStatus, jqXHR){
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + object);
            }
        });
        getAccounts();
    });
    
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
            var klantJson = JSON.stringify({
                "voornaam": $("input#voornaam").val(),
                "tussenvoegsel": $("input#tussenvoegsel").val(),
                "achternaam": $("input#achternaam").val(),
                "email": $("input#email").val(),
                "idKlant": klant.idKlant
            });
            $.ajax({
                type: 'PUT',
                contentType: 'application/json',
                url: restURL,
                data: klantJson,
                dataType: 'json',
                success: function(data, textStatus, jqXHR){
                    //alert("Success!");
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("Error: " + textStatus + "\n" + errorThrown);
                }
            });
            editKlantClicked = false;
            getKlant();
        }
    });
    
    //edit adres
    $(document).on("click", "td#editAdres button", function(){
        event.preventDefault();
        var id = {klantId : klantId, adresId : event.target.id};
        var idParam = $.param(id);
        location.href = "editAdres.html?" + idParam;
    });
    
    //edit account
    var editAccountClicked = false;
    $(document).on("click", "td#editAccount button", function(){
        if(editAccountClicked === false){
            var existingAccountNaam = $("tr#" + event.target.id + " td#accountNaam").text();
            $("tr#" + event.target.id + " td#accountNaam").empty();
            $("td#accountNaam").append("<input type='text' size=30 id='editAccountNaam' value='" + existingAccountNaam + "'/>");
            editAccountClicked = true;
        }
        else {
            var accountJson = JSON.stringify({
                "accountNaam": $("input#editAccountNaam").val(),
                "creatieDatum": $("tr#" + event.target.id + " td#accountCreatieDatum").text(),
                "klantidKlant": klant
            });
            $.ajax({
                type: 'PUT',
                contentType: 'application/json',
                url: accountURL + "/" + event.target.id,
                data: accountJson,
                dataType: 'json',
                success: function(data, textStatus, jqXHR){
                    //alert("Success!");
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("Error: " + textStatus + "\n" + errorThrown + "\n" + accountJson);
                }
            });
            editAccountClicked = false;
            getAccounts();
        }
    });
    
    //delete adres
    $(document).on("click", "td#deleteAdres button", function(){
        var deleteAdresURL = adresURL + "/" + event.target.id;
        ajaxDelete(deleteAdresURL);
        getAdressen();
    });
    
    //delete account
    $(document).on("click", "td#deleteAccount button", function(){
        var deleteAccountURL = accountURL + "/" + event.target.id;
        ajaxDelete(deleteAccountURL);
        getAccounts();
    });
    
    //delete bestelling
    $(document).on("click", "td#deleteBestelling button", function(){
        var deleteBestellingURL = bestellingURL + "/" + event.target.id;
        ajaxDelete(deleteBestellingURL);
        getBestellingen();
    });
    
    //helper functions
    
    //generic delete function based on url
    function ajaxDelete(URL){
        $.ajax({
            type: 'DELETE',
            url: URL,
            success: function(data, textStatus, jqXHR){
                //alert("Success!" + URL);
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Error: " + textStatus + "\n" + errorThrown + "\n" + URL);
            }
        });
    }
    
});

