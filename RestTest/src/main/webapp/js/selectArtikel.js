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
    var klantId = getUrlParameter('klantId');
    var artURL = "http://localhost:40847/RestTest/rest/artikel";
    var bestURL = "http://localhost:40847/RestTest/rest/klant/" + klantId + "/bestelling/" + bestellingId;
    var bhaURL = "http://localhost:40847/RestTest/rest/bestellinghasartikel";
    var selectedArtikel = null;
    var selectedBestelling = null;
    var alleArtikelen = [];
    
    getArtikellijst();
    
    function getArtikellijst() {
        $.getJSON(artURL, function(result){
            alleArtikelen = result;
            $.each(result, function(i, field){
                $("#selectArtikelBody").append("<tr id='" + field.idArtikel + "'></tr>");
                    for (var p in field){
                        if(i===0){
                            $("#selectArtikelTitle").append("<th>" + p + "</th>");
                        }
                        $("#selectArtikelBody tr#" + field.idArtikel).append("<td id='" + p + "'>" + field[p] + "</td>");
                    }
                $("#selectArtikelBody tr#" + field.idArtikel).append("<td class='selecteerArtikel'><button type='button' id='"+ i + "'>Selecteer</button>");
                });
            });
        }
        
        //select artikel
        $(document).on("click", "td#selecteerArtikel button", function(event){
           event.preventDefault();
           selectedArtikel = alleArtikelen[event.target.id];
           $("#selectArtikelBody tr").removeClass('highlight');
           $("#selectArtikelBody tr#" + selectedArtikel.idArtikel).addClass("highlight");
        }); 
        
        //add artikel
        $("button#voegArtikelToe").click(function(event){
          event.preventDefault();
          
          var jsonBHA = JSON.stringify({
             "artikelidArtikel": selectedArtikel,
             "bestellingidBestelling": selectedBestelling,
             "aantal": 1
          });
          
          ajaxCreateBHA(bhaURL, jsonBHA);
          terugNaarBestelling();
        });
        
        //getBestelling
        $.getJSON(bestURL, function(result){
               selectedBestelling = result;
           });
                    
        
        $("button#terugNaarBestelling").click(function(event){
            event.preventDefault();
            terugNaarBestelling();
        });
        
        function terugNaarBestelling(event) {
            event.preventDefault();
            var id = {bestellingId : bestellingId};
            var idParam = $.param(id);
            window.location.href = "viewBestelling.html?" + idParam;
        }
        
        function ajaxCreateBHA (URL, object) {
           $.ajax({
              type: 'POST',
                contentType: 'application/json',
                url: URL,
                dataType: "application/json",
                data: object,
                success: function(data, textStatus, jqXHR){
                    //alert(formToJson() + 'is toegevoegd.');
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert('Fout bij toevoegen artikel:' + "\n" + errorThrown + "\n" + object);
                }
            });
        }
    });
