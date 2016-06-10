$(document).ready(function(){
    
    //Helper methods
    
    //get a url parameter based on name
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
    var adresId = getUrlParameter('adresId');
    var klantUrl = "http://localhost:8080/RestTest/rest/klant/" + klantId;
    var restUrl = klantUrl + "/adres";
    var adresUrl = restUrl + "/" + adresId;
    var allAdresUrl = "http://localhost:8080/RestTest/rest/adres";
    var initAdresType = null;
    var initAdres = null;
    var klantHasAdresId = 0;
    
    
    //get adres based on url
    $.getJSON(adresUrl, function(result){
        $("input#straatnaam").val(result.adresidAdres.straatnaam);
        $("input#huisnummer").val(result.adresidAdres.huisnummer);
        $("input#postcode").val(result.adresidAdres.postcode);
        $("input#woonplaats").val(result.adresidAdres.woonplaats);
        initAdresType = result.adrestypeidAdrestype.idAdrestype;
        initAdres = result.adresidAdres;
        klantHasAdresId = result.idKlanthasadres;
    });

    //get all adres types
    $.getJSON(allAdresUrl + "/adresType", function(result){
        $.each(result, function(i, adresType){
            $("select#adresType").append("<option id='" + adresType.idAdrestype + "' value='" + adresType.adresType + "'>" + adresType.adresType + "</option>");
            if(adresType.idAdrestype === initAdresType){
                $("option#" + adresType.idAdrestype).attr('selected', 'selected');
            }
        });
    });
    
    //get klant based on url
    var klant = null;
    $.getJSON(klantUrl, function(result){
        klant = result;
    });
    
    //edit klant has adres
    function editAdres(data){
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: adresUrl,
            data: data,
            dataType: 'json',
            success: function(data, textStatus, jqXHR){
                //alert(data);
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert("Error: " + textStatus + "\n" + errorThrown + "\n" + data);
            }
        });
    }
    
    //edit adres directly
    function updateAdres(data){
        $.ajax({
            type: 'PUT',
            contentType: 'application/json',
            url: allAdresUrl + "/" + initAdres.idAdres,
            data: data,
            dataType: 'json',
            success: function(data, textStatus, jqXHR){
                //alert(data);
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert("Error: " + textStatus + "\n" + errorThrown + "\n" + data);
            }
        });
    }

    //functions for buttons
    
    //go back to viewKlant
    $("button#backToKlant").click(function() {
        var id = {klantId : klant.idKlant};
        var idParam = $.param(id);
        window.location.href = "viewKlant.html?" + idParam;
    });

    //editAdresType clicked
    $("button#editAdres").click(function(){
        event.preventDefault();
        if($("form#editAdres").valid()){
            var adresType = $("option:selected");
            var adresJson = JSON.stringify({ //For some reason, adres has to be edited first this way
                "idAdres": initAdres.idAdres, "straatnaam": $("input#straatnaam").val(), "huisnummer": $("input#huisnummer").val(),
                    "postcode": $("input#postcode").val(), "woonplaats": $("input#woonplaats").val()
            });
            updateAdres(adresJson);

            var klantHasAdresJson = JSON.stringify({ //now klant has adres can be updated
                "adresidAdres": 
                        {"idAdres": initAdres.idAdres, "straatnaam": $("input#straatnaam").val(), "huisnummer": $("input#huisnummer").val(),
                    "postcode": $("input#postcode").val(), "woonplaats": $("input#woonplaats").val()} ,
                "adrestypeidAdrestype": {"idAdrestype": adresType.attr('id'), "adresType": adresType.val()} ,
                "idKlanthasadres": klantHasAdresId,
                "klantidKlant": {"idKlant": klant.idKlant, "voornaam": klant.voornaam, "tussenvoegsel": klant.tussenvoegsel,
                    "achternaam": klant.achternaam, "email": klant.email}
            });
            editAdres(klantHasAdresJson);
            var id = {klantId : klantId};
            var idParam = $.param(id);
            window.location.href = "viewKlant.html?" + idParam;
        }
    });
    
    //validate adres
    $("form#editAdres").validate({
        rules: { //add rules
            straatnaam:{
                required: true,
                maxlength: 34
            },
            huisnummer:{
                required: true,
                maxlength: 10
            },
            postcode:{
                required: true,
                maxlength: 6,
                minlength: 6
            },
            woonplaats: {
                required: true,
                maxlength: 30
            }
        },
        messages: {
            straatnaam: {
                required: "Straatnaam is verplicht",
                maxlength: jQuery.validator.format("Straatnaam mag maximaal {0} karakters lang zijn") //{0} is a callback to the maxlength value
            },
            huisnummer: {
                required: "Huisnummer is verplicht",
                maxlength: jQuery.validator.format("Huisnummer mag maximaal {0} karakters lang zijn")
            },
            postcode: {
                required: "Postcode is verplicht",
                maxlength: jQuery.validator.format("Postcode moet {0} karakters lang zijn"),
                minlength: jQuery.validator.format("Postcode moet {0} karakters lang zijn")
            },
            woonplaats: {
                required: "Woonplaats is verplicht",
                maxlength: jQuery.validator.format("Woonplaats mag maximaal {0} karakters lang zijn")
            }
        }
    });
});



