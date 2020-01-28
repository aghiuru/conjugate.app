sh prod-build.sh

aws s3 sync --acl public-read --sse --delete resources/public/ s3://www.conjugate.app

aws cloudfront create-invalidation --distribution-id E1FTNHB8ZZQTGK --paths "/js/compiled/app.js"
