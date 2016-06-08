/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
    var URL = "http://localhost:40847/RestTest/rest/bestelling_has_artikel"; //andere link?
    
    $.getJSON(URL, function(result){
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
           ajaxCreateBHA(URL);
        }); 
        
       function ajaxCreateBHA (URL) {
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
        
    });
