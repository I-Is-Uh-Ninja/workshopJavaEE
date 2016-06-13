$(document).ready(function(){
    var URL = "http://localhost:8080/RestTest/rest/artikel";
    
    getArtikelen();
    
    function getArtikelen(){
        $.getJSON(URL, function(result){
            $("#artikelBody").empty();
            $("#artikelTitle").empty();
            $.each(result, function(i, field){
                $("#artikelBody").append("<tr id='" + field.idArtikel + "'></tr>");
                for (var p in field){
                    if(i===0){
                        $("#artikelTitle").append("<th>" + p + "</th>");
                    }
                    $("tr#" + field.idArtikel).append("<td class='" + p + "'>" + field[p] + "</td>");
                }
                $("tr#" + field.idArtikel).append("<td class='edit'><button type='button' id='"+ field.idArtikel + "'>Bewerken</button>");
                $("tr#" + field.idArtikel).append("<td id='delete'><button type='button' id='"+ field.idArtikel + "'>Verwijder artikel</button>");
            });
        });
    }

    /*$(document).on("click", "td#view button", function(){
        var currentId = {artikelId : event.target.id};
        var idParam = $.param(currentId);
        location.href = "viewArtikel.html?" + idParam; 
    });*/
    
    $(document).on("click", "td#delete button", function(event){
        var delURL = URL + "/" + event.target.id;
        ajaxDeleteArtikel(delURL);
    });
    
     $("#nieuwArtikel").submit(function(){
        if($("form#nieuwArtikel").valid()){
            ajaxCreateArtikel(URL);
        }
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
                getArtikelen();
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
                getArtikelen();
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Fout bij verwijderen artikel: " + textStatus + "\n" + errorThrown + "\n" + URL);
            }    
        });
    }
    
    //edit artikel
    var editArtikelClicked = false;
    $(document).on("click", "td.edit button", function(event){
        event.preventDefault();
        if(editArtikelClicked === false) {
            var existingArtikelnaam = $("tr#" + event.target.id + " td.artikelnaam").text();
            var existingArtikelnummer = $("tr#" + event.target.id + " td.artikelnummer").text();
            var existingArtikelomschrijving = $("tr#" + event.target.id + " td.artikelomschrijving").text();
            var existingArtikelprijs = $("tr#" + event.target.id + " td.artikelprijs").text();
            
            $("tr#" + event.target.id + " td.artikelnaam").empty();
            $("tr#" + event.target.id + " td.artikelnummer").empty();
            $("tr#" + event.target.id + " td.artikelomschrijving").empty();
            $("tr#" + event.target.id + " td.artikelprijs").empty();
            
            $("tr#" + event.target.id + " td.artikelnaam").append("<input type='text' size=30 name='artikelnaam' id='editArtikelnaam' value='" + existingArtikelnaam + "' /></td>");
            $("tr#" + event.target.id + " td.artikelnummer").append("<input type='text' size=30 name='artikelnummer' id='editArtikelnummer' value='" + existingArtikelnummer + "' /></td>");
            $("tr#" + event.target.id + " td.artikelomschrijving").append("<input type='text' size=40 name='artikelomschrijving' id='editArtikelomschrijving' value='" + existingArtikelomschrijving + "' /></td>");  
            $("tr#" + event.target.id + " td.artikelprijs").append("<input type='text' size=6 name='artikelprijs' id='editArtikelprijs' value='" + existingArtikelprijs + "' /></td>"); 
            editArtikelClicked = true;
        } else {
            if($("form#alleArtikelen").valid()){
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
                   },
                   complete: function(){
                        editArtikelClicked = false;
                        getArtikelen();
                   }
                });
                
                //getArtikelen();
            }
        }
    });
    //add regex method to validator
    $.validator.addMethod("regex", function(value, element, regexpr) {          
        return regexpr.test(value); //test if the value matches the regular expression
   }, "Please enter a valid value."); //add default message
    
    //validator
    $("form#nieuwArtikel").validate({
        rules: { //add rules
            artikelnaam:{
                required: true,
                maxlength: 80
            },
            artikelnummer:{
                required: true,
                maxlength: 45
            },
            artikelprijs:{
                required: true,
                maxlength: 6,
                regex: /^\d{1,4}(\.{1}\d{1,2}){0,1}$/
            },
            artikelomschrijving: {
                maxlength: 80
            }
        },
        messages: {
            artikelnaam: {
                required: "Artikelnaam is verplicht",
                maxlength: jQuery.validator.format("Artikelnaam mag maximaal {0} karakters lang zijn") //{0} is a callback to the maxlength value
            },
            artikelnummer: {
                required: "Artikelnummer is verplicht",
                maxlength: jQuery.validator.format("Artikelnummer mag maximaal {0} karakters lang zijn")
            },
            artikelprijs: {
                required: "Artikelprijs is verplicht",
                maxlength: jQuery.validator.format("Artikelprijs mag maximaal {0} nummers lang zijn"),
                regex: "Mag alleen nummers bevatten, met 4 cijfers voor, en 2 cijfers na de komma"
            },
            artikelomschrijving: {
                maxlength: jQuery.validator.format("Artikelomschrijving mag maximaal {0} karakters lang zijn")
            }
        }
    });
    $("form#alleArtikelen").validate({
        rules: { //add rules
            artikelnaam:{
                required: true,
                maxlength: 80
            },
            artikelnummer:{
                required: true,
                maxlength: 45
            },
            artikelprijs:{
                required: true,
                maxlength: 6,
                regex: /^\d{1,4}(\.{1}\d{1,2}){0,1}$/
            },
            artikelomschrijving: {
                maxlength: 80
            }
        },
        messages: {
            artikelnaam: {
                required: "Artikelnaam is verplicht",
                maxlength: jQuery.validator.format("Artikelnaam mag maximaal {0} karakters lang zijn") //{0} is a callback to the maxlength value
            },
            artikelnummer: {
                required: "Artikelnummer is verplicht",
                maxlength: jQuery.validator.format("Artikelnummer mag maximaal {0} karakters lang zijn")
            },
            artikelprijs: {
                required: "Artikelprijs is verplicht",
                maxlength: jQuery.validator.format("Artikelprijs mag maximaal {0} nummers lang zijn"),
                regex: "Mag alleen nummers bevatten, met 4 cijfers voor, en 2 cijfers na de komma"
            },
            artikelomschrijving: {
                maxlength: jQuery.validator.format("Artikelomschrijving mag maximaal {0} karakters lang zijn")
            }
        },
        errorElement : 'div',
        errorLabelContainer: '.errorContainer'
    });
});