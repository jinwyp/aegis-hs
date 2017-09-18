/**
 * Created by jin on 7/18/17.
 */


const gulp = require('gulp');

const revCollector = require('gulp-rev-collector');


const sourcePath = {
    'html'                             : '../../backend/src/views/dist/**/*',
    'jsoutput'                         : '../dist/jsoutput/**/*',
    "manifest"                         : '../dist/rev-manifest/*.json'
};
const distPath = {
    'html'                             : '../../backend/src/views/dist/',
    'jsoutput'                         : '../dist/jsoutput/'
};



gulp.task('replace', ['sass-production'],  function () {
    // 替换CSS中的图片
    //gulp.src(['rev/**/*.json', 'dist/styles/**/*.css'])
    //    .pipe( plugins.revCollector() )
    //    .pipe( gulp.dest(distPaths.css) );

    // 替换Html模版文件
    gulp.src([sourcePath.manifest, sourcePath.html])
        .pipe( revCollector({
            revSuffix : '[\.-][0-9a-z]{8,20}-?'
        }) )
        .pipe( gulp.dest(distPath.html) );

});
