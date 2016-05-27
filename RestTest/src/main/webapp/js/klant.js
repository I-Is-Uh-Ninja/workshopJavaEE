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
            $("tr#" + field.idKlant).append("<td id='select'><button type='button' id='"+ field.idKlant + "'>Selecteer</button>");
        });
    });

    var selectedId = 0;

    $(document).on("click", "td#select button", function(){
        alert("button clicked");
        selectedId = $(this).attr('id');
        $("tr").removeClass('highlight');
        $("tr#" + selectedId).addClass('highlight');
        $('button#deleteKlant').removeAttr("hidden");
    });

    $("#nieuwKlant").submit(function(){
        //$.post(restURL, formToJson(), alert(formToJson()));
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: restURL,
            dataType: "application/json",
            data: formToJson(),
            success: function(data, textStatus, jqXHR){
                alert(formToJson());
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('Error: ' + errorThrown + formToJson());
            }
        });
    });

    function formToJson(){
        return JSON.stringify({
            "achternaam": $("#achternaam").val(),
            "email": $("#email").val(),
            "tussenvoegsel": $("#tussenvoegsel").val(),
            "voornaam": $("#voornaam").val()
        });
    }

    $('button#deleteKlant').click(function(){
        var URL = restURL + "/" + selectedId;
        $.ajax({
            type: 'DELETE',
            url: URL,
            success: function(data, textStatus, jqXHR){
                alert(url);
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert(url);
            }
        });
    });
});
