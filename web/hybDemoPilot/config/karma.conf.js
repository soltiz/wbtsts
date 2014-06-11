module.exports = function(config){
    config.set({
    basePath : '../',

    files : [
      'app/lib/angular/angular.js',
      'app/lib/angular/angular-*.js',
      'test/lib/angular/angular-mocks.js',
      'node_modules/jquery/dist/jquery.js',
      'node_modules/jasmine-jquery/lib/jasmine-jquery.js',
      'app/js/**/*.js',
      'app/lib/xml2json.js',
      'test/unit/**/*.js',
     {pattern: 'test/unit/mock/*.html', watched: true, served: true, included: false}
 
    ],

    exclude : [
      'app/lib/angular/angular-loader.js',
      'app/lib/angular/*.min.js',
      'app/lib/angular/angular-scenario.js'
    ],

    autoWatch : true,

    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-junit-reporter',
            'karma-coverage',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine'
            ],

 preprocessors: {
      // source files, that you wanna generate coverage for
      // do not include tests or libraries
      // (these files will be instrumented by Istanbul)
      'app/js/*.js': ['coverage']
    },

    reporters: ['progress','junit','coverage'],

    junitReporter : {
      outputFile: 'test_out/unit.xml',
      suite: ''
    },

 // optionally, configure the reporter
    coverageReporter: {
      type : 'html',
      dir : 'test_out/coverage/'
    }

})}
