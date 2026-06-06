#!/usr/bin/env bash
set -euo pipefail

REPO_URL="${REPO_URL:-https://github.com/GuuiSOares/nutrispace-devops.git}"
OUTPUT_FILE="${OUTPUT_FILE:-azure/vm-info.env}"
AZ="${AZ:-az}"

[[ -f "$OUTPUT_FILE" ]] || { echo "Execute azure/provision-vm.sh antes."; exit 1; }
# shellcheck disable=SC1090
source "$OUTPUT_FILE"

echo "Publicando aplicacao na VM..."
$AZ vm run-command invoke \
  --resource-group "$RESOURCE_GROUP" \
  --name "$VM_NAME" \
  --command-id RunShellScript \
  --scripts "
    set -e
    if [ ! -d /home/${ADMIN_USERNAME}/nutrispace-devops ]; then
      git clone ${REPO_URL} /home/${ADMIN_USERNAME}/nutrispace-devops
      chown -R ${ADMIN_USERNAME}:${ADMIN_USERNAME} /home/${ADMIN_USERNAME}/nutrispace-devops
    fi
    cd /home/${ADMIN_USERNAME}/nutrispace-devops
    git pull
    cp -n .env.example .env 2>/dev/null || true
    docker compose up -d --build
    docker compose ps
  "

echo ""
echo "Deploy enviado. Aguarde alguns minutos (build + Oracle na primeira vez)."
echo "Swagger: http://${VM_PUBLIC_IP}:8080/swagger-ui.html"
