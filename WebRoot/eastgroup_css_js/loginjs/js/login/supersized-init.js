jQuery(function($){

    $.supersized({

        // Functionality
        slide_interval     : 2000,    // Length between transitions
        transition         : 1,    // 0-None, 1-Fade, 2-Slide Top, 3-Slide Right, 4-Slide Bottom, 5-Slide Left, 6-Carousel Right, 7-Carousel Left
        transition_speed   : 1000,    // Speed of transition
        performance        : 1,    // 0-Normal, 1-Hybrid speed/quality, 2-Optimizes image quality, 3-Optimizes transition speed // (Only works for Firefox/IE, not Webkit)

        // Size & Position
        min_width          : 0,    // Min width allowed (in pixels)
        min_height         : 0,    // Min height allowed (in pixels)
        vertical_center    : 1,    // Vertically center background
        horizontal_center  : 1,    // Horizontally center background
        fit_always         : 0,    // Image will never exceed browser width or height (Ignores min. dimensions)
        fit_portrait       : 1,    // Portrait images will not exceed browser height
        fit_landscape      : 0,    // Landscape images will not exceed browser width

        // Components
        slide_links        : 'blank',    // Individual links for each slide (Options: false, 'num', 'name', 'blank')
        slides             : [    // Slideshow Images
                                 
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/1.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/2.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/3.jpg'},
								 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/4.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/5.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/6.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/7.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/8.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/9.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/10.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/11.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/12.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/13.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/14.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/15.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/16.jpg'},
                                 {image : 'eastgroup_css_js/images/loginimage/images/login/backgrounds/17.jpg'}
                       ]

    });

});
