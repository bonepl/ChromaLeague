$razer_service_name = 'Razer Chroma SDK Server'

function Get-ChromaSDK-State {
  Get-Service $razer_service_name | Select -expand Status
}

$current_status = Get-ChromaSDK-State
if ( $current_status -ne 'Running' ) {
  if ( $current_status -ne 'Stopped' ) {
    Write-Output "$razer_service_name is not running, stopping process"
    $id = Get-WmiObject Win32_Service -Filter "Name LIKE '$razer_service_name'" | Select -expand ProcessId
    Stop-Process -Id $id -Force
  }

  $current_status = Get-ChromaSDK-State
  if ( $current_status -eq 'Stopped' ) {
    Write-Output "$razer_service_name is stopped, starting process"
    Get-Service $razer_service_name | Start-Service
  } else {
    Write-Output "ERROR: Couldn't stop $razer_service_name"
  }
} else {
  Write-Output "$razer_service_name is already running"
}

