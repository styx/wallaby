#!/usr/bin/env ruby

require 'fileutils'

fname = File.basename(ARGV.shift)


FileUtils.rm "./drawable-hdpi/#{fname}", force: true
FileUtils.rm "./drawable-mdpi/#{fname}", force: true
FileUtils.rm "./drawable-xhdpi/#{fname}", force: true
FileUtils.rm "./drawable-xxhdpi/#{fname}", force: true

FileUtils.cp fname, "./drawable-hdpi/"
FileUtils.cp fname, "./drawable-mdpi/"
FileUtils.cp fname, "./drawable-xhdpi/"
FileUtils.cp fname, "./drawable-xxhdpi/"

`mogrify -resize 48x48 ./drawable-hdpi/#{fname}`
`mogrify -resize 32x32 ./drawable-mdpi/#{fname}`
`mogrify -resize 64x64 ./drawable-xhdpi/#{fname}`
`mogrify -resize 96x96 ./drawable-xxhdpi/#{fname}`
