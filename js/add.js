const form = $("#form");

$("#dvd").on('click', () => {
    form.append("<label for='ageMin' id='lbl-ageMin'>Âge minimum</label>")
    form.append("<input type='number' id='ageMin' name='ageMin'>")
});

$("#book").on('click', () => {
    $("input").remove("#ageMin");
    $("label").remove("#lbl-ageMin");
});