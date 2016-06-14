$(document).ready(function(){
    var restURL = "http://localhost:8080/RestTest/rest/klant";
/*
    $.getJSON(restURL, function(result){
        $.each(result, function(i, field){
            $("#klantenBody").append("<tr id='" + field.idKlant + "'></tr>");
            for (var p in field){
                if(i===0){
                    $("#klantenTitle").append("<th>" + p + "</th>");
                }
                $("tr#" + field.idKlant).append("<td>" + field[p] + "</td>");
            }
            if (i===0){
                $("#klantenTitle").append("<th>Actie</th>");
            }
            $("tr#" + field.idKlant).append("<td id='view'><button type='button' id='"+ field.idKlant + "'>Bekijk klant</button>");
            $("tr#" + field.idKlant).append("<td id='delete'><button type='button' id='"+ field.idKlant + "'>Verwijder klant</button>");
        });
    });*/
    
    //get all klanten
    $.ajax({
        type: 'GET',
        url: restURL,
        dataType: "json",
        success: function(data){
            displayKlantTable(data);
        }
    });
    
    //get klanten in function to be called somewhere else
    function getKlanten(){
        $.ajax({
            type: 'GET',
            url: restURL,
            dataType: "json",
            success: function(data){
                displayKlantTable(data);
            }
        });
    }
    
    //display a table of all klanten
    function displayKlantTable(klanten){
        $("#klantenBody").empty();
        $.each(klanten, function(i, field){//for each klant
            $("#klantenBody").append("<tr id='" + field.idKlant + "'></tr>"); //add row
            $("tr#" + field.idKlant).append("<td>" + field.idKlant + "</td>");
            $("tr#" + field.idKlant).append("<td>" + field.voornaam + "</td>");
            $("tr#" + field.idKlant).append("<td>" + field.tussenvoegsel + "</td>");
            $("tr#" + field.idKlant).append("<td>" + field.achternaam + "</td>");
            $("tr#" + field.idKlant).append("<td>" + field.email + "</td>");
            $("tr#" + field.idKlant).append("<td id='view'><button type='button' id='"+ field.idKlant + "'>Bekijk klant</button>"); //add view button
            $("tr#" + field.idKlant).append("<td id='delete'><button type='button' id='"+ field.idKlant + "'>Verwijder klant</button>"); //add delete button
        });
    }
    
    //method for clicking the view-button -> go to viewKlant.html with klant ID as parameter
    $(document).on("click", "td#view button", function(event){
        var id = {klantId : event.target.id};
        var idParam = $.param(id);
        window.location.href = "viewKlant.html?" + idParam; 
    });
    
    //method for clicking the delete-button -> make ajax delete request for deleting klant
    $(document).on("click", "td#delete button", function(event){
        var URL = restURL + "/" + event.target.id;
        ajaxDelete(URL);
    });
    
    //method for creating a new klant
    $("#nieuwKlant").submit(function(event){
        event.preventDefault();
        //validate
        if($("#nieuwKlant").valid()){ //calls the validate method
            //now create
            ajaxCreate(restURL);
        }
    });
    
    //convert the input values to json string
    function formToJson(){
        return JSON.stringify({
            "achternaam": $("#achternaam").val(),
            "email": $("#email").val(),
            "tussenvoegsel": $("#tussenvoegsel").val(),
            "voornaam": $("#voornaam").val()
        });
    }
    
    //validator for nieuwKlant form
    $("#nieuwKlant").validate({
        rules: { //add rules
            voornaam:{
                required: true,
                maxlength: 50
            },
            tussenvoegsel:{
                maxlength: 10
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
    
    //CRUD ajax calls
    function ajaxCreate(URL){
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: URL,
            dataType: "application/json",
            data: formToJson(),
            success: function(data, textStatus, jqXHR){
                getKlanten();
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + formToJson());
            }
        });
    }
    
    function ajaxDelete(URL){
        $.ajax({
            type: 'DELETE',
            url: URL,
            success: function(data, textStatus, jqXHR){
                getKlanten();
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Error: " + textStatus + "\n" + errorThrown + "\n" + URL);
            }
        });
    }
});
