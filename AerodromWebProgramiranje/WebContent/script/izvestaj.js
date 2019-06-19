$(function(){
    $("#nav-placeholder").load("modules/nav.html")

    var $loggedUser = JSON.parse(localStorage.getItem('user'));
    if($loggedUser==null){
        alert("Nemate pristup ovoj stranici.")
        location.href = "/webp/";
    } else {
        if(!$loggedUser.admin){
            alert("Nemate dozvolu za pristup ovoj stranici.")
            location.href = "/webp/";
        }
    }


    var inverse = false;

    $("#izvestajForm").submit(function(e){
        e.preventDefault();

        var $datumOd = $("#datum_od").val();
        var $datumDo = $("#datum_do").val();

        $.ajax({
            type:'GET',
            url:'http://localhost:8080/webp/report',
            data:{
                datum_od:$datumOd,
                datum_do:$datumDo
            },

            success:function(data){
                $("#resoult-placeholder").show();
                $("h3").text("Izvestaj : " + $datumOd.replace("T"," ") + " - " + $datumDo.replace("T"," "))
                $izvestaj = JSON.parse(data);
                $("tbody,tfoot").empty();
                $.each($izvestaj.izvestaji,function(i,element){

                    var $tr = $('<tr>').append(

                        $('<td>').text(element.aerodromNaziv),
                        $('<td>').text(element.brojLetova),
                        $('<td>').text(element.brojProdatihKarata),
                        $('<td>').text(element.ukupnaCenaKarata)

                    ).appendTo($("#izvestaj_table tbody"));

                })

                var $footerTr = $('<tr>').append(
                    $('<td>').text("Ukupno"),
                    $('<td>').text($izvestaj.ukupanBrojLetova),
                    $('<td>').text($izvestaj.ukupanBrojProdatihKarata),
                    $('<td>').text($izvestaj.ukupnaCenaProdatihKarata)
                ).css("font-weight","Bold").appendTo($("#izvestaj_table tfoot"))
                
         

                if (!$.fn.dataTable.isDataTable( '#izvestaj_table' ) ) {
                    $("#izvestaj_table").DataTable({
                        "searching":false
                    });
                }

            }

            
        })
        
    });

    // $(".sortable-th").click(function(){
                
    //     var header = $(this),
    //         index = header.index();
            
    //     header
    //         .closest('table')
    //         .find('td')
    //         .filter(function(){
    //             return $(this).index() === index;
    //         })
    //         .sortElements(function(a, b){
                
    //             a = $(a).text();
    //             b = $(b).text();
                
    //             return (
    //                 isNaN(a) || isNaN(b) ?
    //                     a > b : +a > +b
    //                 ) ?
    //                     inverse ? -1 : 1 :
    //                     inverse ? 1 : -1;
                    
    //         }, function(){
    //             return this.parentNode;
    //         });
        
    //     inverse = !inverse;
        
    // });
    


});