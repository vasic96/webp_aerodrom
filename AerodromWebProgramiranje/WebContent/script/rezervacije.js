$(document).ready(function () {
    $("#nav-placeholder").load("modules/nav.html")

    $loggedUser = JSON.parse(localStorage.getItem("user"));
    if ($loggedUser == null) {
        $(".container").hide();
    } else {
        ucitajRezervacije();
    }

    function ucitajRezervacije() {
        $.get("http://localhost:8080/webp/rezervacija", function (data, status, xhr) {
            if (status == "success") {
                $.each(JSON.parse(data), function (i, rezervacija) {

                    var $tr = $('<tr>').append(

            
                        $('<td>').append($('<a>').attr("href","rezervacija.html?id="+rezervacija.id).text(rezervacija.datumRezervacije.replace("T", " "))),
                        $('<td>').append($('<a>').attr("href", "let.html?let_id=" + rezervacija.polazniLetId).text(rezervacija.polazniLetAerodrom)),
                        $('<td>').text(rezervacija.sedistePolazniLet),
                        $('<td>').append((rezervacija.povratniLetId == 0) ? $('<a>').text("-") : $('<a>').attr("href", "let.html?let_id=" + rezervacija.povratniLetId).text(rezervacija.povratniLetAerodrom)),
                        $('<td>').text((rezervacija.povratniLetId <= 0) ? "-" : rezervacija.sedistePovratniLet),
                        $('<td>').append($('<a>').attr("href", "/my-profile.html?username=" + rezervacija.korisnikUsername).text(rezervacija.korisnikUsername)),
                        $('<td>').append(rezervacija.ukupnaCena)

                    ).appendTo($("#rezervacije_table tbody"))
                })

                $("#rezervacije_table").DataTable();
            }
        });
    }

});