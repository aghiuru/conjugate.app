aws s3 mb s3://www.conjugate.app
aws s3 website s3://www.conjugate.app --index-document index.html --error-document error.html
aws s3 sync --acl public-read --sse --delete resources/public/ s3://www.conjugate.app
