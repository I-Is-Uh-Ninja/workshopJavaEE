$(document).ready(function(){
    var URL = "http://localhost:40847/RestTest/rest/artikel";
    
    $.getJSON(URL, function(result){
        $.each(result, function(i, field){
            $("#artikelBody").append("<tr id='" + field.idArtikel + "'></tr>");
                for (var p in field){
                    if(i===0){
                        $("#artikelTitle").append("<th>" + p + "</th>");
                    }
                    $("tr#" + field.idArtikel).append("<td id='" + p + "'>" + field[p] + "</td>");
                }
            $("tr#" + field.idArtikel).append("<td id='edit'><button type='button' id='"+ field.idArtikel + "'>Bewerken</button>");
            $("tr#" + field.idArtikel).append("<td id='delete'><button type='button' id='"+ field.idArtikel + "'>Verwijder artikel</button>");
            });
        });

    /*$(document).on("click", "td#view button", function(){
        var currentId = {artikelId : event.target.id};
        var idParam = $.param(currentId);
        location.href = "viewArtikel.html?" + idParam; 
    });*/
    
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
                //alert(formToJson() + 'is toegevoegd.');
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
    
    //edit artikel
    var editArtikelClicked = false;
    $(document).on("click", "td#edit button", function(){
        if(editArtikelClicked === false) {
            var existingArtikelnaam = $("tr#" + event.target.id + " td#artikelnaam").text();
            var existingArtikelnummer = $("tr#" + event.target.id + " td#artikelnummer").text();
            var existingArtikelomschrijving = $("tr#" + event.target.id + " td#artikelomschrijving").text();
            var existingArtikelprijs = $("tr#" + event.target.id + " td#artikelprijs").text();
            
            $("tr#" + event.target.id + " td#artikelnaam").empty();
            $("tr#" + event.target.id + " td#artikelnummer").empty();
            $("tr#" + event.target.id + " td#artikelomschrijving").empty();
            $("tr#" + event.target.id + " td#artikelprijs").empty();
            
            $("tr#" + event.target.id + " td#artikelnaam").append("<input type='text' size=30 id='editArtikelnaam' value='" + existingArtikelnaam + "' /></td>");
            $("tr#" + event.target.id + " td#artikelnummer").append("<input type='text' size=30 id='editArtikelnummer' value='" + existingArtikelnummer + "' /></td>");
            $("tr#" + event.target.id + " td#artikelomschrijving").append("<input type='text' size=80 id='editArtikelomschrijving' value='" + existingArtikelomschrijving + "' /></td>");  
            $("tr#" + event.target.id + " td#artikelprijs").append("<input type='text' size=6 id='editArtikelprijs' value='" + existingArtikelprijs + "' /></td>"); 
            editArtikelClicked = true;
        } else {
            var artikelJson = JSON.stringify({
                "idArtikel": event.target.id,
                "artikelnaam": $("input#editArtikelnaam").val(),
                "artikelnummer": $("input#editArtikelnummer").val(),
                "artikelomschrijving": $("input#editArtikelomschrijving").val(),
                "artikelprijs": $("input#editArtikelprijs").val()
            });
            $.ajax({
               type: 'PUT',
               contentType: 'application/json',
               url: URL + "/" + event.target.id,
               data: artikelJson,
               dataType: 'json',
               success: function(data, textStatus, jqXHR){
                    //alert("Artikel gewijzigd naar: " + artikelJson);
               },
               error: function(jqXHR, textStatus, errorThrown){
                   //alert("Error: " + textStatus + "\n" + errorThrown + "\n" + artikelJson);
               } 
            });
            editArtikelClicked = false;
            //getArtikelen();
        }
    });
});