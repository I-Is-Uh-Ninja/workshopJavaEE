/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    var bestellingId = getUrlParameter('bestellingId');
    var artURL = "http://localhost:40847/RestTest/rest/artikel";
    var bestURL = "http://localhost:40847/RestTest/rest/bestelling" + bestellingId;
    var bhaURL = "http://localhost:40847/RestTest/rest/bestelling_has_artikel";
    var selectedBestelling = null;
    
    $.getJSON(artURL, function(result){
        $.each(result, function(i, field){
            $("#selectArtikelBody").append("<tr id='" + field.idArtikel + "'></tr>");
                for (var p in field){
                    if(i===0){
                        $("#selectArtikelTitle").append("<th>" + p + "</th>");
                    }
                    $("tr#" + field.idArtikel).append("<td id='" + p + "'>" + field[p] + "</td>");
                }
            $("tr#" + field.idArtikel).append("<td id='addToBestelling'><button type='button' id='"+ field.idArtikel + "'>Voeg aan bestelling toe</button>");
            });
        });
        
        $(document).on("click", "td#addToBestelling button", function(){
           ajaxCreateBHA(bhaURL);
        }); 
        
       function ajaxCreateBHA (bhaURL) {
           $.ajax({
              type: 'POST',
                contentType: 'application/json',
                url: bhaURL,
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
        
        //getter selectedBestelling
        $.getJSON(bestURL, function(result) {
           selectedBestelling = result; 
        });
        
        // werkt nog niet
        $("button#terugNaarBestelling").click(function(){
            var id = {bestellingID : selectedBestelling.bestellingId};
            var idParam = $.param(id);
            window.location.href = "viewBestelling.html?" + idParam;
        });
        
    });
