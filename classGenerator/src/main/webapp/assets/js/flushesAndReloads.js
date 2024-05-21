
    function clearFieldsAndReload() {
    // Clear input fields
    let inputFields = document.querySelectorAll('input[type="text"]');
    inputFields.forEach(function(input) {
    input.value = '';
});

    // Reset data table (assuming it's inside a form with ID 'registration-form')
    document.getElementById('fields-table').reset();


        // ** When I put 'class-generator-form' as elementId , it flushes the
        // ** data table right but after that it will creat a class with
        // ** same class name but without fields. fields map gets empty ,but it
        // ** looks like createJavaFile() method gets called again .
        //document.getElementById('class-generator-form').reset();

    // Reload the page
    window.location.reload();
}

