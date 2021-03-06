$(document).ready(function(){
    
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
    var klantURL = "http://localhost:8080/RestTest/rest/klant/" + klantId;
    var restURL = klantURL + "/adres";
    var adresURL = "http://localhost:8080/RestTest/rest/adres";
    var selectedAdres = 0;
    var selectedKlant = null;
    var allAdressen = [];
    
    //getters for tables
    
    //get all existing adressen
    
    getAllAdressen();
    
    function getAllAdressen(){
        $.getJSON(adresURL, function(result){
            allAdressen = result;
            $("#existingAdresBody").empty();
            $.each(result, function(i, adres){
                $("#existingAdresBody").append("<tr id='" + adres.idAdres + "'></tr>");
                $("#existingAdresBody tr#" + adres.idAdres).append("<td>" + adres.straatnaam + "</td>");
                $("#existingAdresBody tr#" + adres.idAdres).append("<td>" + adres.huisnummer + "</td>");
                $("#existingAdresBody tr#" + adres.idAdres).append("<td>" + adres.postcode + "</td>");
                $("#existingAdresBody tr#" + adres.idAdres).append("<td>" + adres.woonplaats + "</td>");
                $("#existingAdresBody tr#" + adres.idAdres).append("<td class='selectAdres'><button type='button' id='"+ i + "'>Selecteer</button></td>");
            });
        });
    }
    
   
    //get all adres types
    $.getJSON(adresURL + "/adresType", function(result){
        $.each(result, function(i, adresType){
            $("#selectedAdresType").append("<option value='" + adresType.idAdrestype + "'>" + adresType.adresType + "</option>");
            if (i===0){
                $("option:first").attr('selected','selected');
            }
        });
    });
    
    //other getters
    
    //getter for selected klant
    $.getJSON(klantURL, function(result){
        selectedKlant = result;
    });
    
    //methods for buttons
    
    //go back to viewKlant
    $("button#backToKlant").click(function(event) {
        event.preventDefault();
        var id = {klantId : selectedKlant.idKlant};
        var idParam = $.param(id);
        window.location.href = "viewKlant.html?" + idParam;
    });
    
    //select an existing adres
    $(document).on("click","td.selectAdres button", function(event){
        event.preventDefault();
        selectedAdres = allAdressen[event.target.id];
        $("#existingAdresBody tr").removeClass('highlight');
        $("#existingAdresBody tr#" + selectedAdres.idAdres).addClass("highlight");
    });
    
    //add an existing adres
    $(document).on('click',"button.addExistingAdres",function(event){
        //alert("Adres: " + selectedAdres.idAdres + ", klant: " + selectedKlant.idKlant);
        event.preventDefault(); //important: if removed, you aren't redirected
        var adresTypeId = $("select#selectedAdresType").find(":selected").val();
        var adresTypeName = $("select#selectedAdresType").find(":selected").text();
        
        var jsonObject = JSON.stringify({
            "adresidAdres": 
                    {"idAdres": selectedAdres.idAdres, "straatnaam": selectedAdres.straatnaam, "huisnummer": selectedAdres.huisnummer,
                "postcode": selectedAdres.postcode, "woonplaats": selectedAdres.woonplaats} ,
            "adrestypeidAdrestype": {"idAdrestype": adresTypeId, "adresType": adresTypeName} ,
            "klantidKlant": {"idKlant": selectedKlant.idKlant, "voornaam": selectedKlant.voornaam, "tussenvoegsel": selectedKlant.tussenvoegsel,
                "achternaam": selectedKlant.achternaam, "email": selectedKlant.email}
        });
        addAdresToKlant(restURL, jsonObject);
        /*
        var id = {klantId : selectedKlant.idKlant};
        var idParam = $.param(id);
        window.location.href = "viewKlant.html?" + idParam;
         */
    });
    
    //add new adres
    $("button.addNewAdres").click(function(event){
        event.preventDefault();
        if ($("form#addExistingAdres").valid()){
            var newAdres = JSON.stringify({
                "straatnaam": $("input#straatnaam").val(),
               "huisnummer": $("input#huisnummer").val(),
               "postcode": $("input#postcode").val(),
               "woonplaats": $("input#woonplaats").val()
            });

            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: adresURL,
                dataType: "application/json",
                data: newAdres,
                success: function(data, textStatus, jqXHR){
                    //ajaxCreate = data;
                    getAllAdressen();
                },
                error: function(jqXHR, textStatus, errorThrown){
                    //alert('Error: ' + errorThrown + object);
                }
            });
        }
    });
   
   //helper functions
   
   //getter for an adres by id
    var getAdres = function getAdres(id){
        var URL = adresURL + "/" + id;
        $.ajax({
            type: 'GET',
            url: URL,
            data: getAdres,
            dataType: "json", //for some reason, this has to be json and not application/json
            success: function(data, textStatus, jqXHR){
                //alert(formToJson());
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + getAdres);
            }
        });
    };
    
    //ajax create call that posts an object to the URL
    var ajaxCreate = function ajaxCreate(URL, object){
        
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: URL,
            dataType: "application/json",
            data: object,
            success: function(data, textStatus, jqXHR){
                //ajaxCreate = data;
                alert(textStatus);
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + object);
                alert(textStatus);
            }
        });
    };
    
    function addAdresToKlant(URL, object){
        $.ajax({
            type: 'POST',
            contentType: 'application/json',
            url: URL,
            dataType: "application/json",
            data: object,
            success: function(data, textStatus, jqXHR){
                //ajaxCreate = data;
                //alert(textStatus);
            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('Error: ' + errorThrown + object);
                alert("Error: " + errorThrown);
            },
            complete: function(){
                var id = {klantId : selectedKlant.idKlant};
                var idParam = $.param(id);
                window.location.href = "viewKlant.html?" + idParam;
            }
        });
    };
    
    //validate adres
    $("form#addExistingAdres").validate({
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