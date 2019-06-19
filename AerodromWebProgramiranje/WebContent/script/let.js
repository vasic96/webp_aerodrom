var $letId;
var $let;

$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)')
        .exec(window.location.search);

    return (results !== null) ? results[1] || 0 : false;
}

$(function () {
    $("#nav-placeholder").load("modules/nav.html")


    $letId = $.urlParam("let_id");

    getLet($letId);
    $loggedUser = JSON.parse(localStorage.getItem("user"));
    if ($loggedUser == null) {
        $(".container").hide();
    }
    console.log($loggedUser);
    if ($loggedUser.admin) {
        console.log("Jeste admin");
        $("#table-placeholder").show();
        $("#editLetButton").prop("disabled",false);
        $("#deleteButton").prop("disabled",false);
        ucitajRezervacije($letId);
    }

    function ucitajRezervacije($letId){

        $.get("http://localhost:8080/webp/rezervacija_let?tip=rezervacija&let_id="+$letId,function(data,status,xhr){
            if(status=="success"){
                $.each(JSON.parse(data),function(i,rezervacija){
                    var $tip = (rezervacija.polazniLetId==$letId) ? "Polazni":"Povratni";
                    var $sediste = (rezervacija.polazniLetId==$letId) ? rezervacija.sedistePolazniLet : rezervacija.sedistePovratniLet;

                    var $tr = $('<tr>').append(

                        $('<td>').text(rezervacija.datumRezervacije.replace("T"," ")),
                        $('<td>').text($sediste),
                        $('<td>').text($tip),
                        $('<td>').append($('<a>').attr("href","/my-profile.html?username="+rezervacija.korisnikUsername).text(rezervacija.korisnikUsername))

                    ).appendTo($("#rezervacije_table tbody"))
                })

                $("#rezervacija_table").DataTable({"searching":false})
            }
        });

        $.get("http://localhost:8080/webp/rezervacija_let?tip=karta&let_id="+$letId,function(data,status,xhr){
            if(status=="success"){
                $.each(JSON.parse(data),function(i,rezervacija){
                    var $tip = (rezervacija.polazniLetId==$letId) ? "Polazni":"Povratni";
                    var $sediste = (rezervacija.polazniLetId==$letId) ? rezervacija.sedistePolazniLet : rezervacija.sedistePovratniLet;
                    var $tr = $('<tr>').append(

                        $('<td>').text(rezervacija.datumProdajeKarte.replace("T"," ")),
                        $('<td>').text($sediste),
                        $('<td>').text($tip),
                        $('<td>').append($('<a>').attr("href","/my-profile.html?username="+rezervacija.korisnikUsername).text(rezervacija.korisnikUsername))

                    ).appendTo($("#karte_table tbody"))
                })

                $("#karte_table").DataTable({"searching":false});
            }
        });




    }

    function getLet($let_id) {
        $.get("http://localhost:8080/webp/let?let_id=" + $let_id, function (data, status, xhr) {
            if (status == "success") {
                var $let = JSON.parse(data);
                if($let.izbrisan){
                    $("#button-placeholder button").prop("disabled",true);
                    $("body").prepend($('<h4>').text("Let je izbrisan i ne moze se vise rezervisati").css("color","red"));
                    populateInfo($let);
                } else {
                    populateModal($let);
                    populateInfo($let);
                    if($let.nevazeci){
                        $("#novaRezervacija").prop("disabled",true);
                    }
                }
            }
        });
    }

    function populateInfo($let) {
        $("#brojLetaView").val($let.broj);
        $("#polazniAerodromView").val($let.polazniAerodromName);
        $("#dolazniAerodromView").val($let.dolazniAerodromName);
        $("#vremePolaskaView").val($let.vremePolaska.replace("T", " "));
        $("#vremeDolaskaView").val($let.vremeDolaska.replace("T", " "));
        $("#cenaView").val($let.cena);
        $("#brojSlobodnihSedistaView").val($let.brojSlobodnihSedista);


    }

    function populateModal($let) {
        console.log($let);
        $("#vremePolaska").val($let.vremePolaska);
        $("#vremeDolaska").val($let.vremeDolaska);
        $("#brojSedistaInput").val($let.brojSedista);
        $("#cenaInput").val($let.cena);
    }

    // $("#editLetForm").validate({
    //     rules: {
    //         vremePolaska: 'required',
    //         vremeDolaska: 'required',
    //         brojSedista: {
    //             required: true,
    //             min: 1
    //         },
    //         cena: {
    //             required: true,
    //             min: 1
    //         }

    //     }, messages: {
    //         vremePolaska: "This field is required",
    //         vremeDolaska: "This field is required",
    //         brojSedista: {
    //             min: "Broj sedista mora biti veci od 1"
    //         },
    //         cena: {
    //             min: "Cena mora biti veca od 1"
    //         }
    //     },
    //     submitHandler: function (form) {
    //         var data = $(form).serializeArray(); //  <----------- 

    //         var obj = {};

    //         data.forEach(function (i,element) {
    //             console.log(element);
    //             obj[element.name] = element.value;
    //         });

    //         console.log(obj);

    //     }
    // });

    $("#editLetForm").submit(function (e) {
        e.preventDefault();
        var data = $(this).serializeArray(); //  <----------- 
        console.log(data);
        var obj = {};

        data.forEach(function (i, element) {
            console.log(i);
            obj[i.name] = i.value;
        });
        obj.id = $letId;
        console.log(obj);

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/webp/LetServlet',
            dataType: 'json',
            data: JSON.stringify(obj),
            headers: {
                "Content-Type": "application/json"
            },
            success: function (success) {
                console.log("Update successful")
                location.reload();
            }

        })

    })

    $("#deleteButton").click(function () {
        console.log("button clicked!");
        if (confirm("Da li zelite da izbrisete let?")) {
            $.ajax({
                url: "http://localhost:8080/webp/LetServlet?letId=" + $letId,
                type: "DELETE",
                success: function (results) {
                    console.log(results);
                    window.location = document.referrer
                }
            })
        }
    });


    $("#novaRezervacija").click(function () {
        $("#broj-sedista-placeholder").load("modules/izaberiSediste.html");
    });





});

