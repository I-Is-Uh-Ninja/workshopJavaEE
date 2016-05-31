$(document).ready(function(){
    var URL = "http://localhost:40847/RestTest/rest/artikel";
    var currentArtikel;
    var currentId = 0;
    
    $.getJSON(URL, function(result){
        $.each(result, function(i, field){
            $("#adresBody").append("<tr>");
                for (var p in field){
                    if(i===0){
                        $("#adresTitle").append("<th>" + p + "</th>")
                    }
                    $("#adresBody").append("<td>" + field[p] + "</td>");
                }
            $("#adresBody").append("</tr>");
            });
        });
    
    $(document).on("click", "td#select button", function(){
        alert("button clicked");
        selectedId = $(this).attr('id');
        $("tr").removeClass('highlight');
        $("tr#" + selectedId).addClass('highlight');
        $('button#deleteArtikel').removeAttr("hidden");
    }); 
    
     $("#nieuwArtikel").submit(function(){
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: URL,
            dataType: "application/json",
            data: formToJson(),
            success: function(data, textStatus, jqXHR){
                alert(formToJson());
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('Fout bij  toevoegen artikel:' + errorThrown + formToJson());
            }
        });
    });
    
    function formToJson(){
        return JSON.stringify({
            "artikelnaam": $("#artikelnaam").val(),
            "artikelnummer": $("#artikelnummer").val(),
            "artikelomschrijving": $("#artikelomschrijving").val(),
            "artikelprijs": $("#artikelprijs").val()
        });
    }
    
     $('button#deleteArtikel').click(function(){
        var URLdel = URL + "/" + selectedId;
        $.ajax({
            type: 'DELETE',
            url: URLdel,
            success: function(data, textStatus, jqXHR){
                alert('verwijderen geslaagd' + url);
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('Fout bij verwijderen' + url);
            }
        });
    });
});