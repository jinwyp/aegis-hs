/**
 * Created by jin on 7/17/17.
 */



const gulp = require('gulp');
const del = require( 'del');


const distPath = {
    'distAll'  : '../dist',
    'htmlTemplate'  : '../../backend/src/views/dist/',

    'stylesheets' : 'css/stylesheets/**/*',
    'autoSprite' : 'css/images/sprite/auto-sprite.*',

    'tsAotCompileToJs' : 'aotCompiled',
    'tsCompileJsBuild' : './jsoutput-temp-prodution/**/*',
    'tsSourceWithHtmlTpl' : './ts/**/*.js.map'

};

gulp.task('clean', function() {
    del.sync([
        distPath.htmlTemplate,
        distPath.distAll,
        distPath.stylesheets,
        distPath.autoSprite,
        distPath.tsAotCompileToJs

    ], {force:true});
});



