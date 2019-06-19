function prikazTabeleLetova(data) {
    $("#let_table tbody tr").remove();
    $.each(JSON.parse(data), function (i, let) {
        var $aa = $('<a>').attr("href", "let.html?let_id=" + let.id).text(let.broj)
        var $tr = $('<tr>').append(
            $('<td>').append($aa),
            $('<td>').text(let.polazniAerodromName),
            $('<td>').text(let.dolazniAerodromName),
            $('<td>').text(let.vremePolaska.replace("T", "  ")),
            $('<td>').text(let.vremeDolaska.replace("T", "  ")),
            $('<td>').text(let.cena)
        ).appendTo($("#let_table tbody"))
    });

}


$(function () {
    var $aerodromi;
    var $dostupniLetovi;
    var $povratniLetId;
    var $cenaPovratniLet = 0;
    var $cenaPolazniLet = 0;

    function syncPrices() {
        $("#ukupnaCenaInput").val(parseFloat($cenaPolazniLet) + parseFloat($cenaPovratniLet));
    }

    $loggeduser = JSON.parse(localStorage.getItem("user"))

    if ($loggeduser == null) {
        $("#noviLetButton").hide();
        $("#rezervacijeLetaButton").hide();
    } else {
        if (!$loggeduser.admin) {
            $("#noviLetButton").prop("disabled", true);
            if ($loggeduser.blokiran) {
                $("#rezervacijeLetaButton").prop("disabled", true);
            }
        }

    }




    $("#noviLetButton").click(function () {
        var date = new Date();
        $('.date-input').attr("min", (date.getFullYear() + '-' +
            date.getMonth() + '-' +
            date.getDate() + "T" + date.getHours() + ":" + date.getMinutes()))
    });

    $("#nav-placeholder").load("modules/nav.html");

    $.get("http://localhost:8080/webp/dostupni_letovi", function (data, status, xhr) {
        if (status == "success") {
            $("#polazniAerodromRezervacijaSelect").append($('<option>').text("").attr("selected", "selected").attr("disabled", "disabled").attr("hidden", "hidden"));
            $.each(JSON.parse(data), function (i, let) {
                $("#polazniAerodromRezervacijaSelect").append($('<option>').text(let.polazniAerodromName + " : " + let.dolazniAerodromName + " : " + let.vremePolaska.replace("T", " ")).attr("value", let.id).attr("cena", let.cena));
            });

        }
    });

    $.get("http://localhost:8080/webp/aerodromi", function (data, status, xhr) {
        console.log("Data: " + data + "\nStatus: " + status + "\nStatus code: " + xhr.status);
        if (xhr.status == "200") {
            $aerodromi = JSON.parse(data);
            populateSelects();
        } else {
            alert("Problem sa serverom " + xhr.status);
        }
    })

    getAll(true);



    var selectState;

    $('.select_aerodrom').change(function () {

        var selectedId = $(this).attr('id');
        var selectedOptionValue = $(this).val();
        console.log(selectState)
        if (selectedId == 'polazniAerodromSelect') {
            $("#dolazniAerodromSelect").find('option[value="' + selectState + '"]').removeAttr('disabled');
            $("#dolazniAerodromSelect").find('option[value="' + selectedOptionValue + '"]').attr('disabled', 'disabled');
            $("#dolazniAerodromSelect").val([]);
            selectState = selectedOptionValue;
        }

    });

    $("#noviLetForm").submit(function (e) {
        e.preventDefault();
        var data = $(this).serializeArray();

        var obj = {};

        data.forEach(function (element) {
            console.log(element);
            obj[element.name] = element.value;
        });

        console.log(obj);

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/webp/LetServlet',
            contentType: "application/json",
            data: JSON.stringify(obj),
            success: function (success) {
                $("#noviLetModal").modal("hide");
                $("#let_table tbody tr").remove();
                getAll();
            },
            error: function () {
                alert("Nesto nije u redu.")
            }

        })
    })

    function populateSelects() {
        $.each($aerodromi, function (i, value) {
            $("#polazniAerodromSelect").append($("<option>").text(value.naziv).attr('value', value.id));
            if (i == 1) {
                $("#dolazniAerodromSelect").append($("<option>").text(value.naziv).attr('value', value.id).attr('selected', 'selected'));
            } else {
                $("#dolazniAerodromSelect").append($("<option>").text(value.naziv).attr('value', value.id));
            }
        })
    }

    function getAll($firstTime) {
        $.get("http://localhost:8080/webp/LetServlet", function (data, status, xhr) {
            console.log("Data: " + data + "\nStatus: " + status + "\nStatus code: " + xhr.status);
            if (xhr.status == "200") {
                prikazTabeleLetova(data);
                if ($firstTime) {

                    $("#let_table").DataTable(
                        { "searching": false }
                    )
                }
            } else {
                alert("Problem sa serverom " + xhr.status);
            }
        })
    }


    $("#serchByBroj").click(function () {
        $("#search-placeholder").load("modules/searchByBroj.html");
    });

    $("#searchByDatum").click(function () {
        $("#search-placeholder").load("modules/searchByDatum.html");
    });

    $("#searchByAerodrom").click(function () {
        $("#search-placeholder").load("modules/searchByAerodrom.html");
    });

    $("#searchByCena").click(function () {
        $("#search-placeholder").load("modules/searchByCena.html");
    });

    $("#getAll").click(function () {
        $("#let_table tbody tr").remove();
        getAll();
        $("#search-placeholder").empty();
    })



    $("#povratniLetCheckBox").click(function (e) {
        if ($(this).is(':checked')) {
            $("#povratniLetDiv").show();
            ucitajPovratneLetove($("#polazniAerodromRezervacijaSelect").val());
        } else {
            $("#povratniAerodromSelect").val([]);
            $("#sedistePovratniSelect").val([]);
            $("#povratniLetDiv").hide();
            $cenaPovratniLet = 0;
            syncPrices();
        }
    });

    $("#closeRezervacijaModal").click(function () {
        $("#rezervacijaForm").trigger("reset");
    })

    $("#polazniAerodromRezervacijaSelect").change(function () {
        ucitajSlobodnaMesta($(this).val());
        $("#povratniLetCheckBox").prop("disabled", false).prop("checked", false);
        $("#povratniLetDiv").hide();
        $cenaPolazniLet = $('option:selected', this).attr("cena");
        syncPrices();

    });

    $("#povratniAerodromSelect").change(function () {
        ucitajMestaZaPovratniLet($(this).val());
        $cenaPovratniLet = $('option:selected', this).attr("cena");
        syncPrices();
    });


    function ucitajMestaZaPovratniLet($povratniLetId) {
        $.get("http://localhost:8080/webp/slobodna_mesta?let_id=" + $povratniLetId, function (data, status, xhr) {
            if (status == "success") {
                $("#sedistePovratniSelect").empty();
                $("#sedistePovratniSelect").append($('<option>').text("").attr("selected", "selected").attr("disabled", "disabled").attr("hidden", "hidden"));
                $.each(JSON.parse(data), function (i, broj) {
                    $("#sedistePovratniSelect").append($('<option>').text(broj).attr("value", broj));
                });
            }
        });
    }


    function ucitajPovratneLetove($letid1) {
        $.get("http://localhost:8080/webp/povratni_let?let_id=" + $letid1, function (data, status, xhr) {
            if (status == "success") {
                $("#povratniAerodromSelect").empty();
                $("#povratniAerodromSelect").append($('<option>').text("").attr("selected", "selected").attr("disabled", "disabled").attr("hidden", "hidden"));
                $.each(JSON.parse(data), function (i, let) {
                    $("#povratniAerodromSelect").append($('<option>').text(let.polazniAerodrom.naziv + " : " + let.dolazniAerodrom.naziv + " : " + let.vremePolaska).attr("value", let.id).attr("cena", let.cena));
                });
            }
        });
    }

    function ucitajSlobodnaMesta($povratniLetId) {
        $.get("http://localhost:8080/webp/slobodna_mesta?let_id=" + $povratniLetId, function (data, status, xhr) {
            if (status == "success") {
                $("#polazniSedisteSelect").empty();
                $("#polazniSedisteSelect").append($('<option>').text("").attr("selected", "selected").attr("disabled", "disabled").attr("hidden", "hidden"));
                $.each(JSON.parse(data), function (i, broj) {
                    $("#polazniSedisteSelect").append($('<option>').text(broj).attr("value", broj));
                });
            }
        });
    }


    $("#rezervacijaForm").submit(function (event) {
        event.preventDefault();
        var data = $(this).serializeArray();
        console.log(data); //  <----------- 
        var obj = {};
        var $action = $(document.activeElement).val();
        data.forEach(function (element) {
            obj[element.name] = element.value;
        });
        console.log(obj);

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/webp/rezervacija?action=' + $action,
            data: JSON.stringify(obj),
            headers: {
                "Content-Type": "application/json"
            }, success: function () {
                $("#closeRezervacijaModal").trigger("click");
                alert("Uspesno rezervisan let.")
            }
        })
    })



});

