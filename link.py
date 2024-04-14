import os

def chklink(link_name, target_path):
    if os.path.exists(link_name):
        try:
            os.chdir(link_name)
            os.chdir('..')
            os.chdir(target_path)
            os.chdir('..')
        except (PermissionError, FileNotFoundError):
            os.system(f'del /q {link_name}')
            os.system(f'mklink /d {link_name} {target_path}')
    else:
        os.system(f'mklink /d {link_name} {target_path}')

def main():
    chklink('n2n_v1', 'bundles\\n2n_meyerd\\n2n_v1')
    chklink('n2n_v2', 'bundles\\n2n_ntop_v2')
    chklink('n2n_v2s', 'bundles\\n2n_meyerd\\n2n_v2')
    chklink('n2n_v3', 'bundles\\n2n_ntop_v3')
    chklink('uip', 'bundles\\uip\\uip')
    chklink('tun2tap', 'bundles\\tun2tap')
    chklink('slog', 'bundles\\slog')

    os.chdir('Hin2n_android\\app\\src\\main\\cpp')
    chklink('n2n_v1', '..\\..\\..\\..\\..\\bundles\\n2n_meyerd\\n2n_v1')
    chklink('n2n_v2', '..\\..\\..\\..\\..\\bundles\\n2n_ntop_v2')
    chklink('n2n_v2s', '..\\..\\..\\..\\..\\bundles\\n2n_meyerd\\n2n_v2')
    chklink('n2n_v3', '..\\..\\..\\..\\..\\bundles\\n2n_ntop_v3')
    chklink('uip', '..\\..\\..\\..\\..\\bundles\\uip\\uip')
    chklink('tun2tap', '..\\..\\..\\..\\..\\bundles\\tun2tap')
    chklink('slog', '..\\..\\..\\..\\..\\bundles\\slog')

if __name__ == "__main__":
    main()
