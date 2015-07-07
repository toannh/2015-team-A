(function($) {
    $(document).ready(function ($) {
        console.log('meeting scheduler');

        $('#UITabPane a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        })
    });
})($);