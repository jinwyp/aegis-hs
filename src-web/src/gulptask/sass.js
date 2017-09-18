/**
 * Created by jin on 7/17/17.
 */



const gulp           = require('gulp');
const sass           = require('gulp-sass');
const spriteSmith    = require('gulp.spritesmith');
const autoPrefixer   = require('gulp-autoprefixer');
const cleanMinifyCSS = require('gulp-clean-css');
const rev            = require('gulp-rev');



const sourcePath = {
    'htmlTemplate'  : '../../backend/src/views/src/**/*',
    'jsLibs'        : 'js/**/*',
    'fonts'         : 'css/fonts/**/*',
    'images'        : 'css/images/**/*',
    'imagesSprites' : 'css/images/sprite/auto-sprite/**/*',
    'scss'          : 'css/scss/**/*.scss'
};

const distPath = {
    'htmlTemplate'                     : '../../backend/src/views/dist/',
    'jsLibs'                           : '../dist/js/',
    'fonts'                            : '../dist/css/fonts/',
    'images'                           : '../dist/css/images/',
    'imagesSprites'                    : 'css/images/sprite/auto-sprite.png',
    'imagesSpritesScssOutput'          : 'css/scss/helpers/_auto_sprite.scss',
    'imagesSpritesOutputReferringPath' : '/static/css/images/sprite/auto-sprite.png',
    'css'                              : '../dist/css/stylesheets/',
    'manifest'                         : '../dist/rev-manifest/'
};




// Html Views
gulp.task('htmlTemplate', function() {
    gulp.src(sourcePath.htmlTemplate)
        .pipe(gulp.dest(distPath.htmlTemplate))
});


// Optimize images
gulp.task('images', ['sprite'],  function() {
    gulp.src(sourcePath.images)
        .pipe(gulp.dest(distPath.images))
    gulp.src(sourcePath.fonts)
        .pipe(gulp.dest(distPath.fonts))
    gulp.src(sourcePath.jsLibs)
        .pipe(gulp.dest(distPath.jsLibs))
});


// CSS auto sprite
gulp.task('sprite', function () {
    const spriteData = gulp.src(sourcePath.imagesSprites).pipe(spriteSmith({
        imgName:  distPath.imagesSprites ,
        imgPath: distPath.imagesSpritesOutputReferringPath,
        cssName:  distPath.imagesSpritesScssOutput ,
        cssFormat:  'scss',
        algorithm : 'alt-diagonal',
        padding: 20
    }));
    return spriteData.pipe(gulp.dest(''));
});


// Compile css and automatically prefix stylesheets
gulp.task('sass', [ 'images' ], function() {
    return gulp.src(sourcePath.scss)
        .pipe(sass({
            precision       : 10,
            outputStyle     : 'compact',
            errLogToConsole : true
        }).on('error', sass.logError))
        // .pipe(autoPrefixer({
        //     browsers: ['> 5%', 'Last 2 versions'],
        //     cascade: true, //是否美化属性值 默认：true 像这样：
        //     //-webkit-transform: rotate(45deg);
        //     //        transform: rotate(45deg);
        //     remove:true //是否去掉不必要的前缀 默认：true
        // }))
        // .pipe(optimizerCss({debug: true, compatibility: 'ie8'}))
        .pipe(gulp.dest(distPath.css))
});



gulp.task('sass-production', [ 'htmlTemplate', 'images'], function(done) {

    return gulp.src(sourcePath.scss)
        .pipe(sass({
            precision       : 10,
            outputStyle     : 'compact',
            errLogToConsole : true
        }).on('error', sass.logError))
        // .pipe(autoPrefixer({
        //     browsers: ['> 5%', 'Last 2 versions'],
        //     cascade: false
        // }))
        .pipe(cleanMinifyCSS({debug: true, compatibility: 'ie8'}))
        .pipe(rev())
        .pipe(gulp.dest(distPath.css))
        .pipe(rev.manifest('rev-manifest-css.json'))
        .pipe(gulp.dest(distPath.manifest))

});



gulp.task('watchSass', ['sass'], function() {
    gulp.watch(sourcePath.scss, ['sass']);
});


