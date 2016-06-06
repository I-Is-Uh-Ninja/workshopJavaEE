$(document).ready(function(){
    var URL = "http://localhost:8080/RestTest/rest/artikel";
    
    $.getJSON(URL, function(result){
        $.each(result, function(i, field){
            $("#artikelBody").append("<tr id='" + field.idArtikel + "'></tr>");
                for (var p in field){
                    if(i===0){
                        $("#artikelTitle").append("<th>" + p + "</th>");
                    }
                    $("tr#" + field.idArtikel).append("<td id='" + p + "'>" + field[p] + "</td>");
                }
            $("tr#" + field.idArtikel).append("<td id='view'><button type='button' id='"+ field.idArtikel + "'>Bekijk artikel</button>");
            $("tr#" + field.idArtikel).append("<td id='delete'><button type='button' id='"+ field.idArtikel + "'>Verwijder artikel</button>");
            });
        });
    
     $(document).on("click", "td#view button", function(){
        var currentId = {artikelId : event.target.id};
        var idParam = $.param(currentId);
        location.href = "viewArtikel.html?" + idParam; 
    });
    
      $(document).on("click", "td#delete button", function(){
        var delURL = URL + "/" + event.target.id;
        ajaxDeleteArtikel(delURL);
    });
    
     $("#nieuwArtikel").submit(function(){
         ajaxCreateArtikel(URL);
    });
    
    function formToJson(){
        return JSON.stringify({
            "artikelnaam": $("input#artikelnaam").val(),
            "artikelnummer": $("input#artikelnummer").val(),
            "artikelomschrijving": $("input#artikelomschrijving").val(),
            "artikelprijs": $("input#artikelprijs").val()
        });
    }
    
    function ajaxCreateArtikel(URL) {
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: URL,
            dataType: "application/json",
            data: formToJson(),
            success: function(data, textStatus, jqXHR){
                alert(formToJson() + 'is toegevoegd.');
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('Fout bij toevoegen artikel:' + "\n" + errorThrown + "\n" + formToJson());
            }
        });
    }
    
    function ajaxDeleteArtikel(URL) {
        $.ajax({
            type: 'DELETE',
            url: URL,
            succes: function(data, textStatus, jqXHR) {
                alert("Artikel verwijderd");
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Fout bij verwijderen artikel: " + textStatus + "\n" + errorThrown + "\n" + URL);
            }    
        });
    }
});