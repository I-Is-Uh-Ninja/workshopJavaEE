$(document).ready(function(){
    var restURL = "http://localhost:8080/RestTest/rest/klant";

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
    });


    $(document).on("click", "td#view button", function(){
        var id = {klantId : event.target.id};
        var idParam = $.param(id);
        location.href = "viewKlant.html?" + idParam; 
    });
    
    $(document).on("click", "td#delete button", function(){
        var URL = restURL + "/" + event.target.id;
        ajaxDelete(URL);
        /*
        $.ajax({
            type: 'DELETE',
            url: URL,
            success: function(data, textStatus, jqXHR){
                //alert("Success!\n" + URL);
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Error: " + errorThrown + "\n" + URL);
            }
        });*/
    });

    $("#nieuwKlant").submit(function(){
        //$.post(restURL, formToJson(), alert(formToJson()));
        ajaxCreate(restURL);
    });

    function formToJson(){
        return JSON.stringify({
            "achternaam": $("#achternaam").val(),
            "email": $("#email").val(),
            "tussenvoegsel": $("#tussenvoegsel").val(),
            "voornaam": $("#voornaam").val()
        });
    }
    
    //CRUD ajax calls
    function ajaxCreate(URL){
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: URL,
            dataType: "application/json",
            data: formToJson(),
            success: function(data, textStatus, jqXHR){
                //alert(formToJson());
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('Error: ' + errorThrown + formToJson());
            }
        });
    }
    
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
