$(function () {
    $("#nav-placeholder").load("modules/nav.html")
    $.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)')
            .exec(window.location.search);

        return (results !== null) ? results[1] || 0 : false;
    }

    $id = $.urlParam("id");
    console.log($id);
    $loggedUser = JSON.parse(localStorage.getItem("user"));
    if ($loggedUser == null) {
        $(".container").hide();
    } else {
        ucitajRezervaciju($id);
    }

    $("#kupiKartuBtn").click(function () {
        if (confirm("Zelite li da kupite kartu?")) {
            $.get("http://localhost:8080/webp/kupi_kartu?id=" + $id, function (data, status, xhr) {
                if (status == "success") {
                    location.reload();
                }
            });
        }
    });

    $("#sedisteForm").submit(function(e){
        e.preventDefault();
        var $data = {
            sediste_polazni:$("#sedistePolazniLet").val(),
            id:$rezervacija.id
        }
        if($rezervacija.povratniLetId!=0){
            $data.sediste_povratni = $("#sedistePovratniLet").val()
        }

        $.ajax({
            type:'GET',
            url:'http://localhost:8080/webp/promeni_sediste',
            data:$data,
            success:function(success){
                console.log("Uspesno promenjeno sediste");
                location.reload();
            },
            fail:function(){
                alert("Nesto nije u redu");
            }
        })
    });

    $("#izbrisiRezervacijuBtn").click(function () {
        if (confirm("Da li zelite da izbrisete rezervaciju?")) {
            $.ajax({
                type: 'DELETE',
                url: 'http://localhost:8080/webp/rezervacija' + '?' + $.param({ "id": $id }),
                success: function () {
                    window.location = document.referrer;
                },
                error: function (jqXHR) {
                    console.log(jqXHR.status)
                    alert("Nesto nije u redu.")
                }
            })
        }
    });


    $("#imePrezimeForm").submit(function (e) {
        e.preventDefault();
        $novoIme = $("#novoIme").val();
        $novoPrezime = $("#novoPrezime").val();
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/webp/ime_prezime',
            data: {
                novo_ime: $novoIme,
                novo_prezime: $novoPrezime,
                id: $rezervacija.id
            },
            success: function (success) {
                console.log("Successfully changed name and surname");
                location.reload();
            },
            fail: function () {
                alert("Nesto nije u redu.");
            }

        })
    });

    $("#sedistePolazniLetButton").click(function () {

        $("#sedistePolazniLet").append($('<option>').attr("selected", "selected").val($rezervacija.sedistePolazniLet).text($rezervacija.sedistePolazniLet));

        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/webp/slobodna_mesta',
            data: {
                let_id: $rezervacija.polazniLetId
            },
            success: function (data) {
                $.each(JSON.parse(data), function (i, broj) {
                    $("#sedistePolazniLet").append($('<option>').text(broj).attr("value", broj));
                });
            }
        });

        if ($rezervacija.povratniLetId != 0) {
            $("#sedistePovratniLet").append($('<option>').attr("selected", "selected").val($rezervacija.sedistePovratniLet).text($rezervacija.sedistePovratniLet));
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/webp/slobodna_mesta',
                data: {
                    let_id: $rezervacija.povratniLetId
                },
                success: function (data) {
                    $.each(JSON.parse(data), function (i, broj) {
                        $("#sedistePovratniLet").append($('<option>').text(broj).attr("value", broj));
                    });
                }
            });
        }

        $("#sedistaModal").modal('toggle');


    });


    var $rezervacija

    $("#posetiKorisnikaBtn").click(function () {
        window.location.href = "/webp/my-profile.html?username=" + $rezervacija.korisnikUsername;
    });

    $("#posetiPolazniBtn").click(function () {
        window.location.href = "/webp/let.html?let_id=" + $rezervacija.polazniLetId;
    });
    function ucitajRezervaciju($id) {
        $.get("http://localhost:8080/webp/rezervacija_id?id=" + $id, function (data, status, xhr) {
            console.log(status)
            console.log("Status je " + xhr.status)
            if (status == "success") {

                $rezervacija = JSON.parse(data);
                $("#datumRezervacijeView").val($rezervacija.datumRezervacije.replace("T", "-"));
                $("#datumProdajeKarteView").val(($rezervacija.datumProdajeKarte === undefined) ? " " : $rezervacija.datumProdajeKarte.replace("T", " "))
                $("#polazniLetView").val($rezervacija.polazniLetAerodrom);
                $("#polazniLetSedisteView").val($rezervacija.sedistePolazniLet);
                $("#povratniLetView").val($rezervacija.povratniLetAerodrom);
                $("#povratniLetSedisteView").val(($rezervacija.sedistePovratniLet == 0) ? "-" : $rezervacija.sedistePovratniLet)
                $("#korisnikView").val($rezervacija.korisnikUsername);
                $("#ukupnaCenaView").val($rezervacija.ukupnaCena);
                $("#imeView").val($rezervacija.imePutnika);
                $("#prezimeView").val($rezervacija.prezimePutnika)
                $(".rezervacija-only").prop("disabled", ($rezervacija.datumProdajeKarte === undefined) ? false : true);
                $("#povratniLetSedisteButton, #povratniLetButtonm, #sedistePovratniLet").prop("disabled",
                    ($rezervacija.povratniLetAerodrom == "") ? true : false
                )
                $("#novoIme").val($rezervacija.imePutnika);
                $("#novoPrezime").val($rezervacija.prezimePutnika);

            }
        }).fail(function (xhr) {
            $(".container").hide();
            alert("Nemate pristup ovoj stranici ili stranica ne postoji.")
        });
    }

});