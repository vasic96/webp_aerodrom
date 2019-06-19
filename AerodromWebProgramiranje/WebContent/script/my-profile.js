$(function () {

    $("#nav-placeholder").load("modules/nav.html")


    var $currentUser;
    var $viewingMyProfile = true;

    $.urlParam = function (name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)')
            .exec(window.location.search);

        return (results !== null) ? results[1] || 0 : false;
    }

    var $username = $.urlParam("username");
    var $loggedUser = JSON.parse(localStorage.getItem("user"));
    if ($loggedUser == null) {
        $('container').hide();
        alert("Morate biti ulogovani da bi pristupili ovoj stranici");
        location.href = '/webp/';
    } else if ($username == "" || $username == $loggedUser.korisnickoIme) {
        $currentUser = $loggedUser;
        loadUserInfo($currentUser);
        $("#user-info-container").show();
        setupEditInput();
        ucitajRezervacije($currentUser.korisnickoIme);
        $viewingMyProfile = true;
        if($currentUser.blokiran){
            $("#promenaPodatakaButton, #showPasswordDiv").prop("disabled",true)
        }

    } else if (!$loggedUser.admin) {
        alert("Morate biti administrator da bi pristupili ovoj stranici.");
        location.href = "/webp/"
    } else {
        $viewingMyProfile = false;
        getUser($username);
    }



    function getUser($username) {

        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/webp/username',
            data: {
                username: $username
            },
            success: function (data) {
                $currentUser = JSON.parse(data);
                loadUserInfo($currentUser);
                $("#user-info-container").show();
                if (!$currentUser.izbrisan) {
                    setupEditInput();
                    $("#oldPasswordInput").prop("disabled", true)
                    ucitajRezervacije($currentUser.korisnickoIme)
                    $("#adminButtonsPlaceholder").show();
                    $("#promoteButton").prop("disabled", false);
                    if (!$currentUser.admin) {
                        $("#blockButton").text(($currentUser.blokiran) ? "Odblokiraj" : "Blokiraj");
                        $("#blockButton").val(($currentUser.blokiran) ? "unblock" : "block");
                        $("#blockButton").addClass(($currentUser.blokiran) ? "btn-success" : "btn-danger");
                        $("#promoteButton").text("Promovisi u admina").val("promote").addClass("btn-success");
                    } else {
                        $("#blockButton").hide();
                        $("#promoteButton").text("Ogranici na ulogu korisnika").val("demote").addClass("btn-danger");
                    }


                }
            },
            error: function () {
                alert("Trazeni korisnik nije pronadjen.");
                location.href = "/webp/users.html";
            }
        })
    }

    $("#promoteButton").click(function () {
        if (confirm("Da li ste sigurni?")) {
            var $action = $(this).val();
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/webp/role',
                data: {
                    username: $currentUser.korisnickoIme,
                    action: $action
                },
                success: function () {
                    location.reload();
                }
            })

        }


    });


    // if ($userName == "" && $loggedUser != null) {
    //     $currentUser = $loggedUser;
    //     loadUserInfo($currentUser);
    //     $("#user-info-container").show();
    //     setupEditInput();
    //     ucitajRezervacije($currentUser.korisnickoIme)
    // } else if ($loggedUser != null) {
    //     if ($loggedUser.korisnickoIme == $userName) {
    //         $currentUser = $loggedUser;
    //         loadUserInfo($currentUser);
    //         $("#user-info-container").show();
    //         setupEditInput();
    //         ucitajRezervacije($currentUser.korisnickoIme)
    //         return;
    //     }
    //     $.get("http://localhost:8080/webp/KorisnikServlet?username=" + $userName, function (data, status, xhr) {

    //         if (status == "success") {
    //             console.log(data)
    //             if (data != "[null]") {
    //                 console.log("Request successful")
    //                 $("#user-info-container").show();
    //                 $currentUser = JSON.parse(data)[0];
    //                 loadUserInfo($currentUser)
    //                 setupEditInput();
    //                 if (!$loggedUser.admin) {
    //                     $("#button-placeholder").hide();
    //                 } else {
    //                     $("#adminButtonsPlaceholder").show();
    //                     ucitajRezervacije($currentUser.korisnickoIme);
    //                     if ($currentUser.korisnickoIme !== $loggedUser.korisnickoIme) {
    //                         $ViewingMyProfile = false;
    //                         if (!$currentUser.admin) {
    //                             $("#promoteButton").prop("disabled", false);
    //                         }
    //                         $("#blockButton").text(($currentUser.blokiran) ? "Odblokiraj" : "Blokiraj");
    //                         $("#blockButton").val(($currentUser.blokiran) ? "unblock" : "block");
    //                         $("#blockButton").addClass(($currentUser.blokiran) ? "btn-success" : "btn-danger");
    //                     } else {
    //                         $("#blockButton").prop("disabled", true).text("Blokiraj");
    //                     }

    //                 }
    //             }


    //         } else {
    //             $currentUser = null;
    //             $("#user-info-container").hide();
    //         }

    //     });
    // }

    $("#blockButton").click(function () {
        if (confirm("Da li ste sigurni?")) {
            var $action = $(this).val();
            var $username = $currentUser.korisnickoIme;
            $.ajax({
                url: 'http://localhost:8080/webp/block',
                data: {
                    action: $action,
                    username: $username
                },
                success: function () {
                    location.reload();
                },
                error: function () {
                    alert("Nesto nije u redu.")
                }

            })
        }
    });



    function setupEditInput() {
        $("#usernameEditInput").val($currentUser.korisnickoIme)
    }

    function loadUserInfo($user) {
        console.log($user);
        $("#korisnickoImeView").val($user.korisnickoIme);
        $("#datumRegistracijeView").val($user.datumRegistracije.replace("T", " "));
        $("#ulogaView").val(($user.admin) ? "Administraotr" : "Korisnik");
        $("#blokiranVIew").val(($user.blokiran) ? "Blokiran" : "Nije blokiran");
    }




    // $("#usernameEditInput").val($currentUser.korisnickoIme);


    function ucitajRezervacije($username) {

        $("#table-placeholder").show();

        $.get("http://localhost:8080/webp/rezervacija_korisnik?tip=rezervacija&username=" + $username, function (data, status, xhr) {
            if (status == "success") {
                $.each(JSON.parse(data), function (i, rezervacija) {

                    var $tr = $('<tr>').append(

                        $('<td>').text(rezervacija.datumRezervacije.replace("T", " ")),
                        $('<td>').text(rezervacija.polazniLetAerodrom),
                        $('<td>').text((rezervacija.povratniLetId == 0) ? "-" : rezervacija.povratniLetAerodrom),
                        $('<td>').text(rezervacija.sedistePolazniLet),
                        $('<td>').text((rezervacija.povratniLetId <= 0) ? "-" : rezervacija.sedistePovratniLet)

                    ).appendTo($("#rezervacije_table tbody"))
                })
                $("#rezervacije_table").DataTable({"searching":false});

            }
        });

        $.get("http://localhost:8080/webp/rezervacija_korisnik?tip=karta&username=" + $username, function (data, status, xhr) {
            if (status == "success") {
                $.each(JSON.parse(data), function (i, rezervacija) {
                    var $tr = $('<tr>').append(

                        $('<td>').text(rezervacija.datumRezervacije.replace("T", " ")),
                        $('<td>').text(rezervacija.polazniLetAerodrom),
                        $('<td>').text((rezervacija.povratniLetId == 0) ? "-" : rezervacija.povratniLetAerodrom),
                        $('<td>').text(rezervacija.sedistePolazniLet),
                        $('<td>').text((rezervacija.povratniLetId <= 0) ? "-" : rezervacija.sedistePovratniLet)

                    ).appendTo($("#karte_table tbody"))
                })

                $("#karte_table").DataTable({"searching":false});

            }
        });




    }


    $("#nav-placeholder").load("modules/nav.html");

    $("#changePasswordCancel").click(function () {
        $("#changePasswordDiv").hide();
    });

    $("#changeUsernameCancel").click(function () {
        $("#change-username-div").hide();
    });

    $("#showPasswordDiv").click(function () {
        $("#changePasswordDiv").show();
    });

    $("#promenaPodatakaButton").click(function () {
        $("#change-username-div").show();
    });

    $("#changePasswordForm").submit(function (e) {
        var $newPassword = $("#newPasswordInput").val();
        e.preventDefault();
        if ($newPassword != $("#confirmPasswordInput").val()) {
            alert("Lozinke se moraju poklapati!")
        }

        var $data = {
            username: $currentUser.korisnickoIme,
            new_password: $newPassword
        }
        if ($viewingMyProfile) {
            $data.old_password = $("#oldPasswordInput").val();
        }

        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/webp/change_password',
            data: $data,
            success: function () {
                if ($viewingMyProfile) {
                    localStorage.removeItem("user");
                    location.href = "/webp/";
                }
                location.reload();
            }

        })

        // $.get("http://localhost:8080/webp/change_password?old_password=" + $oldPassowrd + "&new_password=" + $newPassword + "&username=" + $currentUser.korisnickoIme
        //     , function (data, status, xhr) {
        //         if (status == "success") {
        //             if ($viewingMyProfile) {
        //                 localStorage.removeItem("user");
        //                 location.href("/webp/");
        //             }
        //             location.reload();
        //         }
        //     });
    });

    $("#changeUsernameForm").submit(function (e) {
        var $newUsername = $("#usernameEditInput").val();
        e.preventDefault();
        $.get("http://localhost:8080/webp/change_username?username=" + $currentUser.korisnickoIme + "&new_username=" + $newUsername
            , function (data, status, xhr) {
                if (status == "success") {
                    if ($viewingMyProfile) {
                        localStorage.removeItem("user");
                        location.href = "/webp/";
                    }
                    location.href = "/webp/my-profile.html?username=" + $newUsername;
                }
            });
    });

});