import logging
from datetime import datetime

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s',
    handlers=[
        logging.FileHandler(f'app_logs.log'),
        logging.StreamHandler()
    ]
)

logger = logging.getLogger(__name__)