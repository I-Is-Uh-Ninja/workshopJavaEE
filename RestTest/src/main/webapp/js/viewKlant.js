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
            $("#accountBody tr#" + account.idAccount).append("<td class='accountNaam'>" + account.accountNaam + "</td>");
            $("#accountBody tr#" + account.idAccount).append("<td class='accountCreatieDatum'>" + account.creatieDatum + "</td>");
            $("#accountBody tr#" + account.idAccount).append("<td class='editAccount'><button type='button' id='"+ account.idAccount + "'>Bewerken</button></td>");
            $("#accountBody tr#" + account.idAccount).append("<td id='deleteAccount'><button type='button' id='"+ account.idAccount + "'>Verwijderen</button></td>");
        });
    }
    
    //bestelling
    var bestellingURL = restURL + "/bestelling";
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
    
    //delete bestelling
    $(document).on("click", "td#deleteBestelling button", function(event){
        $.ajax({
            type: 'DELETE',
            url: bestellingURL + "/" + event.target.id,
            success: function(data, textStatus, jqXHR){
                getBestellingen();
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Error: " + textStatus + "\n" + errorThrown + "\n" + url);
            }
        });
    });
    
    //addBestelling        
    $("button#addBestelling").click(function(event){
        event.preventDefault();
       var bestellingUrl = restURL + "/bestelling";
       var id = {klantId : klantId};
       var jsonBestelling = JSON.stringify({
           "klantIdKlant": id
       });
       $.ajax({
           type: 'POST',
           contentType: 'application/json',
           url: bestellingUrl,
           dataType: "application/json",
           data: jsonBestelling,
            success: function(data, textStatus, jqXHR){
                getBestellingen();
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + object);
            }
        });
    });
    
    //door na viewBestelling
    $(document).on("click", "td#viewBestelling button", function(event) {
        event.preventDefault();
        var id = {klantId : klantId, bestellingId : event.target.id};
        var idParam = $.param(id);
        location.href = "viewBestelling.html?" + idParam;
    });
    
  
    //functions for buttons
    
    //add adres
    $("button#addAdres").click(function(event){
        event.preventDefault();
        var id = {klantId : klantId};
        var idParam = $.param(id);
        location.href = "addAdres.html?" + idParam;
    });
    
    //add account
    $("button#addAccount").click(function(event){
        event.preventDefault();
        if($("form#account").valid()){
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
                    getAccounts();
                },
                error: function(jqXHR, textStatus, errorThrown){
                    //alert('Error: ' + errorThrown + object);
                }
            });
        }
    });
    
    //edit klant
    var editKlantClicked = false;
    $("button#editKlant").click(function(event){
        event.preventDefault();
        if(editKlantClicked === false){
            $("#klantBody tr").empty();
            $("#klantBody tr").append("<td><input type='text' id='voornaam' size='20' value='" + klant.voornaam + "' /></td>");
            $("#klantBody tr").append("<td><input type='text' id='tussenvoegsel' size='10' value='" + klant.tussenvoegsel + "' /></td>");
            $("#klantBody tr").append("<td><input type='text' id='achternaam' size='20' value='" + klant.achternaam + "' /></td>");
            $("#klantBody tr").append("<td><input type='text' id='email' size='40' value='" + klant.email + "' /></td>");
            editKlantClicked = true;
        }
        else{
            if($("form#klant").valid()){
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
                        editKlantClicked = false;
                        getKlant();
                    },
                    error: function(jqXHR, textStatus, errorThrown){
                        alert("Error: " + textStatus + "\n" + errorThrown);
                    }
                });
                
            }
        }
    });
    
    //edit adres
    $(document).on("click", "td#editAdres button", function(event){
        event.preventDefault();
        var id = {klantId : klantId, adresId : event.target.id};
        var idParam = $.param(id);
        location.href = "editAdres.html?" + idParam;
    });
    
    //edit account
    var editAccountClicked = false;
    $(document).on("click", "td.editAccount button", function(event){
        event.preventDefault();
        if(editAccountClicked === false){
            var existingAccountNaam = $("tr#" + event.target.id + " td.accountNaam").text();
            $("tr#" + event.target.id + " td.accountNaam").empty();
            $("tr#" + event.target.id + " td.accountNaam").append("<input type='text' size=30 name='accountnaam' id='editAccountNaam' value='" + existingAccountNaam + "'/>");
            editAccountClicked = true;
        }
        else {
            if($("form#account").valid()){
                var accountJson = JSON.stringify({
                    "accountNaam": $("input#editAccountNaam").val(),
                    "creatieDatum": $("tr#" + event.target.id + " td.accountCreatieDatum").text(),
                    "idAccount": event.target.id,
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
                        editAccountClicked = false;
                        getAccounts();
                    },
                    error: function(jqXHR, textStatus, errorThrown){
                        alert("Error: " + textStatus + "\n" + errorThrown + "\n" + accountJson);
                    }
                });
            }
        }
    });
    
    //delete adres
    $(document).on("click", "td#deleteAdres button", function(event){
        $.ajax({
            type: 'DELETE',
            url: adresURL + "/" + event.target.id,
            success: function(data, textStatus, jqXHR){
                getAdressen();
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Error: " + textStatus + "\n" + errorThrown + "\n" + url);
            }
        });
        
    });
    
    //delete account
    $(document).on("click", "td#deleteAccount button", function(event){
        $.ajax({
            type: 'DELETE',
            url:  accountURL + "/" + event.target.id,
            success: function(data, textStatus, jqXHR){
                getAccounts();
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Error: " + textStatus + "\n" + errorThrown + "\n" + url);
            }
        });
        
    });
    
    //validators
    
    //validate klant
    $("form#klant").validate({
        rules: { //add rules
            voornaam:{
                required: true,
                maxlength: 50
            },
            tussenvoegsel:{
                maxlength: 50
            },
            achternaam:{
                required: true,
                maxlength: 51
            },
            email: {
                required: true,
                email: true,
                maxlength: 80
            }
        },
        messages: {
            voornaam: {
                required: "Voornaam is verplicht",
                maxlength: jQuery.validator.format("Voornaam mag maximaal {0} karakters lang zijn") //{0} is a callback to the maxlength value
            },
            tussenvoegsel: {
                maxlength: jQuery.validator.format("Tussenvoegsel mag maximaal {0} karakters lang zijn")
            },
            achternaam: {
                required: "Achternaam is verplicht",
                maxlength: jQuery.validator.format("Achternaam mag maximaal {0} karakters lang zijn")
            },
            email: {
                required: "E-mail is verplicht",
                maxlength: jQuery.validator.format("E-mail mag maximaal {0} karakters lang zijn"),
                email: "Geen geldig e-mail adres"
            }
        }
    });
    
    //validate accountnaam
    $("form#account").validate({
        rules: { //add rules
            accountnaam:{
                required: true,
                maxlength: 80
            }
        },
        messages: {
            accountnaam: {
                required: "Accountnaam is verplicht",
                maxlength: jQuery.validator.format("Accountnaam mag maximaal {0} karakters lang zijn") //{0} is a callback to the maxlength value
            }
        }
    });
    
});

